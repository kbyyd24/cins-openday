package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MailUpdater;
import cn.edu.swpu.cins.openday.model.http.PasswordUpdater;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;

public interface UserService {
	UserServiceResultEnum signUp(SignUpUser signUpUser, String token);
	UserServiceResultEnum enable(AuthenticatingUser au);
	UserServiceResultEnum signin(SignInUser signInUser);
	UserServiceResultEnum updateMail(MailUpdater mailUpdater);
	UserServiceResultEnum updatePassword(PasswordUpdater passwordUpdater);

}
