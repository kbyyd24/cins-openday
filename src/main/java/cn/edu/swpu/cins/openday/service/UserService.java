package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MailUpdater;
import cn.edu.swpu.cins.openday.model.http.PasswordUpdater;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.service.AuthUser;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;

public interface UserService {
	UserServiceResultEnum signUp(SignUpUser signUpUser, String token);
	UserServiceResultEnum enable(AuthUser au);
	UserSignInResult signin(SignInUser signInUser);
	UserServiceResultEnum updateMail(MailUpdater mailUpdater);
	UserServiceResultEnum updatePassword(PasswordUpdater passwordUpdater);

}
