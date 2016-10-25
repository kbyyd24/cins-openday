package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.model.persistence.User;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;

public interface CacheService {
	CacheResultEnum saveAuthingUser(AuthenticatingUser authenticatingUser);
	String getEnableToken(String mail);
	CacheResultEnum signin(User user);
	CacheResultEnum signout(int id);
	CacheResultEnum removeAuthToken(String mail);
}
