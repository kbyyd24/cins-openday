package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.UserDao;
import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.enums.ExceptionMsgEnum;
import cn.edu.swpu.cins.openday.enums.HttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.exception.CacheException;
import cn.edu.swpu.cins.openday.exception.NoUserToEnableException;
import cn.edu.swpu.cins.openday.exception.OpenDayException;
import cn.edu.swpu.cins.openday.exception.RedisException;
import cn.edu.swpu.cins.openday.model.http.HttpResult;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.model.persistence.User;
import cn.edu.swpu.cins.openday.model.service.AuthUser;
import cn.edu.swpu.cins.openday.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.*;

@Service
public class UserServiceImpl implements UserService {
	private UserDao userDao;
	private CacheService cacheService;
	private TimeService timeService;
	private PasswordEncoderService passwordService;
	private URLCoderService urlCoderService;
	private TokenService tokenService;
	private final MailFormatService mailFormatService;
	private final MailService mailService;

	@Autowired
	public UserServiceImpl(UserDao userDao, CacheService cacheService, TimeService timeService,
	                       PasswordEncoderService passwordService, URLCoderService urlCoderService,
	                       TokenService tokenService, MailFormatService mailFormatService,
	                       MailService mailService) {
		this.userDao = userDao;
		this.cacheService = cacheService;
		this.timeService = timeService;
		this.passwordService = passwordService;
		this.urlCoderService = urlCoderService;
		this.tokenService = tokenService;
		this.mailFormatService = mailFormatService;
		this.mailService = mailService;
	}

	@Override
	@Transactional(rollbackFor = {DataAccessException.class, OpenDayException.class})
	public HttpResult signUp(SignUpUser signUpUser, String token) {
		UserServiceResultEnum resultEnum = saveToDB(signUpUser, token);
		if (resultEnum == ADD_USER_SUCCESS) {
			sendMail(signUpUser, token);
			return new HttpResult(HttpResultEnum.SIGN_UP_USER_SUCCESS);
		}
		return returnSignUpError(resultEnum);
	}

	private void sendMail(SignUpUser signUpUser, String token) {
		String subject = mailFormatService.getSignUpSubject(signUpUser.getUsername());
		String text = mailFormatService.getSignUpContent(signUpUser.getMail(), token);
		try {
			mailService.send(signUpUser.getMail(), subject, text);
		} catch (MessagingException e) {
			throw new OpenDayException(ExceptionMsgEnum.SEND_MAIL_FAILED);
		}
	}

	private HttpResult returnSignUpError(UserServiceResultEnum signUpResult) {
		if (signUpResult == ADD_USER_FAILED) {
			return new HttpResult(HttpResultEnum.SIGN_UP_USER_FAILED);
		} else if (signUpResult == EXISTED_USERNAME_AND_MAIL) {
			return new HttpResult(HttpResultEnum.EXISTED_USERNAME_AND_MAIL);
		} else if (signUpResult == EXISTED_USERNAME) {
			return new HttpResult(HttpResultEnum.EXISTED_USERNAME);
		} else if (signUpResult == EXISTED_MAIL) {
			return new HttpResult(HttpResultEnum.EXISTED_MALI);
		} else if (signUpResult == PASSWORD_NOT_SAME) {
			return new HttpResult(HttpResultEnum.PASSWORD_NOT_SAME);
		}
		return new HttpResult(HttpResultEnum.UNKNOWN_ERROR);
	}

	private UserServiceResultEnum saveToDB(SignUpUser signUpUser, String token) {
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
			CacheResultEnum cacheResultEnum = cacheService.saveAuthingUser(new AuthUser(signUpUser.getMail(), token));
			if (cacheResultEnum == CacheResultEnum.SAVE_SUCCESS) {
				return ADD_USER_SUCCESS;
			} else {
				throw new CacheException(ExceptionMsgEnum.SAVE_CACHE_FAILED);
			}
		}
		return ADD_USER_FAILED;
	}

	@Override
	@Transactional(rollbackFor = {NoUserToEnableException.class, RedisException.class})
	public UserServiceResultEnum enable(AuthUser au) {
		au.setMail(urlCoderService.decode(au.getMail()));
		au.setToken(urlCoderService.decode(au.getToken()));
		String enableToken = cacheService.getEnableToken(au.getMail());
		CacheResultEnum verifyResult = verifyToken(au, enableToken);
		if (verifyResult == CacheResultEnum.ENABLE_TOKEN_SUCCESS) {
			int line = userDao.enable(au.getMail());
			if (line == 1) {
				return ENABLE_TOKEN_SUCCESS;
			} else {
				throw new NoUserToEnableException(ExceptionMsgEnum.CHECK_ENABLE_FAILED);
			}
		} else if (verifyResult == CacheResultEnum.ENABLE_TOKEN_TIMEOUT){
			return UserServiceResultEnum.ENABLE_TOKEN_TIMEOUT;
		} else {
			return UserServiceResultEnum.ENABLE_TOKEN_INVALID;
		}
	}

	private CacheResultEnum verifyToken(AuthUser au, String enableToken) {
		if (enableToken.equals(au.getToken())) {
			long tokenTime = Long.parseLong(enableToken);
			long now = timeService.getCurrentTimeMillis();
			if (now - tokenTime <= 30 * 60 * 1000) {
				return CacheResultEnum.ENABLE_TOKEN_SUCCESS;
			}
			return CacheResultEnum.ENABLE_TOKEN_TIMEOUT;
		} else {
			return CacheResultEnum.ENABLE_TOKEN_INVALID;
		}
	}

	@Override
	public UserServiceResultEnum signOut(AuthUser au) {
		CacheResultEnum signout = cacheService.signout(au);
		if (signout == CacheResultEnum.REMOVE_SUCCESS) {
			return UserServiceResultEnum.SIGN_OUT_SUCCESS;
		}
		return UserServiceResultEnum.SIGN_OUT_FAILED;
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
			result.setStatus(UserServiceResultEnum.LOGIN_SUCCESS.name());
			return result;
		}
		return new UserSignInResult(UserServiceResultEnum.CACHE_FAILED);
	}

}
