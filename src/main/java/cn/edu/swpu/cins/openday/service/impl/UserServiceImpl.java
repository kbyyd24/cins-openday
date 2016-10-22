package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.UserDao;
import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.exception.CacheException;
import cn.edu.swpu.cins.openday.model.http.MailUpdater;
import cn.edu.swpu.cins.openday.model.http.PasswordUpdater;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;
import cn.edu.swpu.cins.openday.service.CacheService;
import cn.edu.swpu.cins.openday.service.UserService;
import cn.edu.swpu.cins.openday.util.PasswordEncodeUtil;
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
	public UserServiceResultEnum enable(AuthenticatingUser au) {
		return null;
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
