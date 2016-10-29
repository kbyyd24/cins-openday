package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.dao.cache.CacheDao;
import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.model.service.AuthUser;
import cn.edu.swpu.cins.openday.service.impl.RedisCacheServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static cn.edu.swpu.cins.openday.enums.CacheResultEnum.SAVE_SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CacheServiceTest {

	@Mock
	private CacheDao cacheDao;

	private CacheService cacheService;

	@Before
	public void setUp() throws Exception {
		cacheService = new RedisCacheServiceImpl(cacheDao);
	}

	@Test
	public void test_saveAuthingUser_success() throws Exception {
		String mail = "mail@mail.com";
		String token = "12";
		AuthUser au = new AuthUser(mail, token);
		doNothing().when(cacheDao).signUp(eq(mail), any(HashMap.class));
		CacheResultEnum resultEnum = cacheService.saveAuthingUser(au);
		assertThat(resultEnum, is(SAVE_SUCCESS));
	}

	@Test
	public void test_signIn_success() throws Exception {
		String token = "token";
		int id = 1;
		String username = "username";
		String mail = "mail@mail.com";
		UserSignInResult signInResult = new UserSignInResult(token, id, username, mail);
		doNothing().when(cacheDao).signIn(eq(mail), any(HashMap.class));
		CacheResultEnum result = cacheService.signIn(signInResult);
		assertThat(result, is(CacheResultEnum.SAVE_SUCCESS));
		verify(cacheDao).signIn(eq(mail), any(HashMap.class));
	}

	@Test
	public void test_signOut_success() throws Exception {
		String mail = "mail@mail.com";
		String token = "123";
		AuthUser au = new AuthUser(mail, token);
		when(cacheDao.getSignToken(mail)).thenReturn(token);
		doNothing().when(cacheDao).signOut(mail);
		CacheResultEnum ret = cacheService.signout(au);
		assertThat(ret, is(CacheResultEnum.REMOVE_SUCCESS));
		verify(cacheDao).getSignToken(mail);
		verify(cacheDao).signOut(mail);
	}

	@Test
	public void test_signOut_invalid_token() throws Exception {
		String mail = "mail@mail.com";
		String token = "123";
		AuthUser au = new AuthUser(mail, token);
		when(cacheDao.getSignToken(mail)).thenReturn(mail);
		CacheResultEnum ret = cacheService.signout(au);
		assertThat(ret, is(CacheResultEnum.INVALID_TOKEN));
		verify(cacheDao).getSignToken(mail);
	}
}