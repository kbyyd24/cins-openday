package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MailUpdater;
import cn.edu.swpu.cins.openday.model.http.PasswordUpdater;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Override
	public UserServiceResultEnum signUp(SignUpUser signUpUser) {
		return null;
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
