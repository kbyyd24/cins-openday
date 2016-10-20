package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.UserDao;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MailUpdater;
import cn.edu.swpu.cins.openday.model.http.PasswordUpdater;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.service.UserService;
import cn.edu.swpu.cins.openday.util.PasswordEncodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.*;

@Service
public class UserServiceImpl implements UserService {
	private UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserServiceResultEnum signUp(SignUpUser signUpUser) {
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
			return ADD_USER_SUCCESS;
		} else {
			// TODO: 16-10-19 deal with add failed
		}
		return ADD_USER_FAILED;
	}

	@Override
	public UserServiceResultEnum enable(String mail) {
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
