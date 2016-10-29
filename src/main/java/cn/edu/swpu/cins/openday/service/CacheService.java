package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;

public interface CacheService {
	CacheResultEnum saveAuthingUser(AuthenticatingUser authenticatingUser);
	String getEnableToken(String mail);
	CacheResultEnum signIn(UserSignInResult user);
	CacheResultEnum signout(int id);
}
