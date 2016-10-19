package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;

public interface CacheService {
	UserServiceResultEnum saveAuthingUser(AuthenticatingUser authenticatingUser);
}
