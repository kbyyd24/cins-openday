package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.cache.CacheDao;
import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;
import cn.edu.swpu.cins.openday.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheServiceImpl implements CacheService {

	private static final String AUTHENTICATION_KEY = "authentication";
	private static final String ONLINE_KEY = "online";

	private CacheDao cacheDao;

	@Autowired
	public RedisCacheServiceImpl(CacheDao cacheDao) {
		this.cacheDao = cacheDao;
	}

	@Override
	public CacheResultEnum saveAuthingUser(AuthenticatingUser au) {
		if (cacheDao.saveEntry(AUTHENTICATION_KEY, au.getMail(), au.getToken())) {
			return CacheResultEnum.SAVE_SUCCESS;
		}
		return CacheResultEnum.SAVE_FAILED;
	}

	@Override
	public String getEnableTokenAndRemove(String mail) {
		Object o = cacheDao.getValue(AUTHENTICATION_KEY, mail);
		removeAuthToken(mail);
		if (o == null) {
			return "";
		}
		return (String) o;
	}

	private CacheResultEnum removeAuthToken(String mail) {
		long num = cacheDao.removeValue(AUTHENTICATION_KEY, mail);
		if (num == 1) {
			return CacheResultEnum.REMOVE_TOKEN_SUCCESS;
		}
		return CacheResultEnum.REMOVE_TOKEN_FAILED;
	}

	@Override
	public CacheResultEnum signin(UserSignInResult user) {
		return null;
	}

	@Override
	public CacheResultEnum signout(int id) {
		return null;
	}
}
