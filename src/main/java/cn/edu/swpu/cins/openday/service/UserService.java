package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;

public interface UserService {
	UserServiceResultEnum signUp(SignUpUser signUpUser);
	UserServiceResultEnum enable(SignUpUser signUpUser);
	UserServiceResultEnum signin(SignUpUser signUpUser);
}
