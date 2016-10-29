package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.UserDao;
import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.exception.CacheException;
import cn.edu.swpu.cins.openday.exception.NoUserToEnableException;
import cn.edu.swpu.cins.openday.exception.RedisException;
import cn.edu.swpu.cins.openday.model.http.MailUpdater;
import cn.edu.swpu.cins.openday.model.http.PasswordUpdater;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.persistence.User;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.*;

@Service
public class UserServiceImpl implements UserService {
	private UserDao userDao;
	private CacheService cacheService;
	private ClockService clockService;
	private PasswordEncoderService passwordService;
	private URLCoderService urlCoderService;
	private TokenService tokenService;

	@Autowired
	public UserServiceImpl(UserDao userDao, CacheService cacheService, ClockService clockService,
	                       PasswordEncoderService passwordService, URLCoderService urlCoderService,
	                       TokenService tokenService) {
		this.userDao = userDao;
		this.cacheService = cacheService;
		this.clockService = clockService;
		this.passwordService = passwordService;
		this.urlCoderService = urlCoderService;
		this.tokenService = tokenService;
	}

	@Override
	@Transactional(rollbackFor = CacheException.class)
	public UserServiceResultEnum signUp(SignUpUser signUpUser, String token) {
		if (!signUpUser.isPasswordValid()) {
			return PASSWORD_NOT_SAME;
		}
		signUpUser.setPassword(passwordService.encode(signUpUser.getPassword()));
		UserServiceResultEnum validRet = userDao.checkNewUser(signUpUser);
		if (validRet != ADD_USER_USABLE) {
			return validRet;
		}
		int line = userDao.signUpUser(signUpUser);
		if (line == 1) {
			CacheResultEnum cacheResultEnum = cacheService.saveAuthingUser(new AuthenticatingUser(signUpUser.getMail(), token));
			if (cacheResultEnum == CacheResultEnum.SAVE_SUCCESS) {
				return ADD_USER_SUCCESS;
			} else {
				throw new CacheException("save authenticating user failed");
			}
		}
		return ADD_USER_FAILED;
	}

	@Override
	@Transactional(rollbackFor = {NoUserToEnableException.class, RedisException.class})
	public UserServiceResultEnum enable(AuthenticatingUser au) {
		au.setMail(urlCoderService.decode(au.getMail()));
		au.setToken(urlCoderService.decode(au.getToken()));
		String enableToken = cacheService.getEnableToken(au.getMail());
		CacheResultEnum verifyResult = verifyToken(au, enableToken);
		if (verifyResult == CacheResultEnum.ENABLE_TOKEN_SUCCESS) {
			int line = userDao.enable(au.getMail());
			if (line == 1) {
				return ENABLE_TOKEN_SUCCESS;
			} else {
				throw new NoUserToEnableException("exception happened in mysql when enable user: " + au.getMail());
			}
		} else if (verifyResult == CacheResultEnum.ENABLE_TOKEN_TIMEOUT){
			return UserServiceResultEnum.ENABLE_TOKEN_TIMEOUT;
		} else {
			return UserServiceResultEnum.ENABLE_TOKEN_INVALID;
		}
	}

	private CacheResultEnum verifyToken(AuthenticatingUser au, String enableToken) {
		if (enableToken.equals(au.getToken())) {
			long tokenTime = Long.parseLong(enableToken);
			long now = clockService.getCurrentTimeMillis();
			if (now - tokenTime <= 30 * 60 * 1000) {
				return CacheResultEnum.ENABLE_TOKEN_SUCCESS;
			}
			return CacheResultEnum.ENABLE_TOKEN_TIMEOUT;
		} else {
			return CacheResultEnum.ENABLE_TOKEN_INVALID;
		}
	}

	@Override
	public UserSignInResult signin(SignInUser signInUser) {
		User user = userDao.signInUser(signInUser);
		if (user == null || user.getId() == null) {
			return new UserSignInResult(UserServiceResultEnum.USER_NOT_EXIST);
		}
		boolean isMatch = passwordService.match(signInUser.getPassword(), user.getPassword());
		if (!isMatch) {
			return new UserSignInResult(UserServiceResultEnum.PASSWORD_NOT_SAME);
		}
		String token = tokenService.createToken();
		UserSignInResult result = new UserSignInResult(token, user);
		CacheResultEnum cacheResult = cacheService.signIn(result);
		if (cacheResult == CacheResultEnum.SAVE_SUCCESS) {
			result.setStatus(UserServiceResultEnum.LOGIN_SUCCESS);
			return result;
		}
		return new UserSignInResult(UserServiceResultEnum.CACHE_FAILED);
	}

	@Override
	public UserServiceResultEnum updateMail(MailUpdater mailUpdater) {
		return null;
	}

	@Override
	public UserServiceResultEnum updatePassword(PasswordUpdater passwordUpdater) {
		return null;
	}
}
