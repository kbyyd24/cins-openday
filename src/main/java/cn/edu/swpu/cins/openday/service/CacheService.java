package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.model.persistence.User;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;

public interface CacheService {
	CacheResultEnum saveAuthingUser(AuthenticatingUser authenticatingUser);
	CacheResultEnum enableAuthingUser(AuthenticatingUser authenticatingUser);
	CacheResultEnum signin(User user);
	CacheResultEnum signout(int id);
}
