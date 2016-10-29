package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.cache.CacheDao;
import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.model.service.AuthUser;
import cn.edu.swpu.cins.openday.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
public class RedisCacheServiceImpl implements CacheService {

	private CacheDao cacheDao;

	@Autowired
	public RedisCacheServiceImpl(CacheDao cacheDao) {
		this.cacheDao = cacheDao;
	}

	@Override
	@Transactional(rollbackFor = DataAccessException.class)
	public CacheResultEnum saveAuthingUser(AuthUser au) {
		HashMap<String, String> entry = new HashMap<>(2);
		entry.put("token", au.getToken());
		cacheDao.signUp(au.getMail(), entry);
		return CacheResultEnum.SAVE_SUCCESS;
	}

	@Override
	public String getEnableToken(String mail) {
		return cacheDao.getEnableToken(mail);
	}

	@Override
	@Transactional(rollbackFor = DataAccessException.class)
	public CacheResultEnum signIn(UserSignInResult user) {
		HashMap<String, String> entry = new HashMap<>(6);
		entry.put("id", user.getId().toString());
		entry.put("username", user.getUsername());
		entry.put("mail", user.getMail());
		entry.put("token", user.getToken());
		cacheDao.signIn(user.getMail(), entry);
		return CacheResultEnum.SAVE_SUCCESS;
	}

	@Override
	@Transactional(rollbackFor = DataAccessException.class)
	public CacheResultEnum signout(AuthUser authUser) {
		String signToken = cacheDao.getSignToken(authUser.getMail());
		if (authUser.getToken().equals(signToken)) {
			cacheDao.signOut(authUser.getMail());
			return CacheResultEnum.REMOVE_SUCCESS;
		}
		return CacheResultEnum.INVALID_TOKEN;
	}
}
