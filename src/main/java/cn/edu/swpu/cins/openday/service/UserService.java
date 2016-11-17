package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.HttpResult;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.model.service.AuthUser;

public interface UserService {

	HttpResult signUp(SignUpUser signUpUser, String token);

	UserServiceResultEnum enable(AuthUser au);

	UserSignInResult signin(SignInUser signInUser);

	UserServiceResultEnum signOut(AuthUser au);

}
