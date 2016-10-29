package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.dao.cache.CacheDao;
import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;
import cn.edu.swpu.cins.openday.service.impl.RedisCacheServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static cn.edu.swpu.cins.openday.enums.CacheResultEnum.SAVE_SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
		AuthenticatingUser au = new AuthenticatingUser(mail, token);
		when(cacheDao.saveEntry(anyString(), eq(mail), eq(token))).thenReturn(true);
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
		when(cacheDao.signIn(signInResult)).thenReturn(true);
		CacheResultEnum result = cacheService.signIn(signInResult);
		assertThat(result, is(CacheResultEnum.SAVE_SUCCESS));
		verify(cacheDao).signIn(signInResult);
	}
}