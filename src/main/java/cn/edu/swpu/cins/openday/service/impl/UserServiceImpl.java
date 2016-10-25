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
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;
import cn.edu.swpu.cins.openday.service.CacheService;
import cn.edu.swpu.cins.openday.service.UserService;
import cn.edu.swpu.cins.openday.util.PasswordEncodeUtil;
import cn.edu.swpu.cins.openday.util.URLCoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.*;

@Service
public class UserServiceImpl implements UserService {
	private UserDao userDao;
	private CacheService cacheService;

	@Autowired
	public UserServiceImpl(UserDao userDao, CacheService cacheService) {
		this.userDao = userDao;
		this.cacheService = cacheService;
	}

	@Override
	@Transactional(rollbackFor = CacheException.class)
	public UserServiceResultEnum signUp(SignUpUser signUpUser, String token) {
		if (!signUpUser.isPasswordValid()) {
			return PASSWORD_NOT_SAME;
		}
		signUpUser.setPassword(PasswordEncodeUtil.encode(signUpUser.getPassword()));
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
		au.setMail(URLCoderUtil.decode(au.getMail()));
		au.setToken(URLCoderUtil.decode(au.getToken()));
		String enableToken = cacheService.getEnableToken(au.getMail());
		CacheResultEnum verifyResult = verifyToken(au, enableToken);
		if (verifyResult == CacheResultEnum.ENABLE_TOKEN_SUCCESS) {
			if (cacheService.removeAuthToken(au.getMail()) ==
							CacheResultEnum.REMOVE_TOKEN_SUCCESS) {
				int line = userDao.enable(au.getMail());
				if (line == 1) {
					return ENABLE_TOKEN_SUCCESS;
				} else {
					throw new NoUserToEnableException("exception happened in mysql when enable user: " + au.getMail());
				}
			} else {
				// TODO: 16-10-25 增强鲁棒性，加入更多redis操作失败的处理
				throw new RedisException("exception happened in redis when remove user: " + au.getMail());
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
			long now = System.currentTimeMillis();
			if (now - tokenTime <= 30 * 60 * 1000) {
				return CacheResultEnum.ENABLE_TOKEN_SUCCESS;
			}
			return CacheResultEnum.ENABLE_TOKEN_TIMEOUT;
		} else {
			return CacheResultEnum.ENABLE_TOKEN_INVALID;
		}
	}

	@Override
	public UserServiceResultEnum signin(SignInUser signInUser) {
		return null;
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
