package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.persistence.User;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;

public interface CacheService {
	UserServiceResultEnum saveAuthingUser(AuthenticatingUser authenticatingUser);
	UserServiceResultEnum enableAuthingUser(AuthenticatingUser authenticatingUser);
	UserServiceResultEnum signin(User user);
	UserServiceResultEnum signout(int id);
}
