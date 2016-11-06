package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.model.service.AuthUser;

public interface CacheService {
	CacheResultEnum saveAuthingUser(AuthUser authUser);
	String getEnableToken(String mail);
	CacheResultEnum signIn(UserSignInResult user);
	CacheResultEnum signout(AuthUser authUser);

	boolean checkToken(AuthUser authUser);
}
