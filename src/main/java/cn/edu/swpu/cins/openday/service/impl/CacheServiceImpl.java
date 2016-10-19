package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.persistence.User;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;
import cn.edu.swpu.cins.openday.service.CacheService;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {
	@Override
	public UserServiceResultEnum saveAuthingUser(AuthenticatingUser authenticatingUser) {
		return null;
	}

	@Override
	public UserServiceResultEnum enableAuthingUser(AuthenticatingUser authenticatingUser) {
		return null;
	}

	@Override
	public UserServiceResultEnum signin(User user) {
		return null;
	}

	@Override
	public UserServiceResultEnum signout(int id) {
		return null;
	}
}
