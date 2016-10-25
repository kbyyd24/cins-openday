package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.dao.persistence.UserDao;
import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.exception.NoUserToEnableException;
import cn.edu.swpu.cins.openday.exception.RedisException;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;
import cn.edu.swpu.cins.openday.service.impl.ClockServiceImpl;
import cn.edu.swpu.cins.openday.service.impl.UserServiceImpl;
import cn.edu.swpu.cins.openday.util.URLCoderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserDao userDao;

	@Mock
	private CacheService cacheService;

	@Mock
	private ClockServiceImpl clockService;

	private UserService userService;

	@Before
	public void setUp() throws Exception {
		userService = new UserServiceImpl(userDao, cacheService, clockService);
	}

	@Test
	public void test_signup_success() throws Exception {
		String username = "username";
		String password = "password";
		String mail = "melo@gaoyuexiang.cn";
		SignUpUser signUpUser =
						new SignUpUser(username, password, password, mail);
		when(userDao.checkNewUser(signUpUser)).thenReturn(ADD_USER_USABLE);
		when(userDao.signUpUser(signUpUser)).thenReturn(1);
		String token = "123";
		AuthenticatingUser au = new AuthenticatingUser(mail, token);
		when(cacheService.saveAuthingUser(au)).thenReturn(CacheResultEnum.SAVE_SUCCESS);
		UserServiceResultEnum userServiceResultEnum =
						userService.signUp(signUpUser, token);
		assertThat(userServiceResultEnum, is(UserServiceResultEnum.ADD_USER_SUCCESS));
	}

	@Test
	public void test_enable_success() throws Exception {
		String mail = "mail@mail.com";
		String baseMail = URLCoderUtil.encode(mail);
		String token = String.valueOf(System.currentTimeMillis());
		String baseToken = URLCoderUtil.encode(token);
		AuthenticatingUser au = new AuthenticatingUser(baseMail, baseToken);
		when(cacheService.getEnableToken(eq(mail))).thenReturn(token);
		when(clockService.getCurrentTimeMillis()).thenCallRealMethod();
		when(cacheService.removeAuthToken(eq(mail))).thenReturn(CacheResultEnum.REMOVE_TOKEN_SUCCESS);
		when(userDao.enable(eq(mail))).thenReturn(1);
		UserServiceResultEnum enableResult = userService.enable(au);
		assertThat(enableResult, is(ENABLE_TOKEN_SUCCESS));
		verify(cacheService).getEnableToken(eq(mail));
		verify(cacheService).removeAuthToken(eq(mail));
		verify(userDao).enable(eq(mail));
	}

	@Test(expected = NoUserToEnableException.class)
	public void test_enable_NoUserToEnableException() throws Exception {
		String mail = "mail@mail.com";
		String baseMail = URLCoderUtil.encode(mail);
		String token = String.valueOf(System.currentTimeMillis());
		String baseToken = URLCoderUtil.encode(token);
		AuthenticatingUser au = new AuthenticatingUser(baseMail, baseToken);
		when(cacheService.getEnableToken(eq(mail))).thenReturn(token);
		when(clockService.getCurrentTimeMillis()).thenCallRealMethod();
		when(cacheService.removeAuthToken(eq(mail))).thenReturn(CacheResultEnum.REMOVE_TOKEN_SUCCESS);
		when(userDao.enable(eq(mail))).thenReturn(0);
		UserServiceResultEnum enableResult = userService.enable(au);
		assertThat(enableResult, is(ENABLE_TOKEN_SUCCESS));
		verify(cacheService).getEnableToken(eq(mail));
		verify(cacheService).removeAuthToken(eq(mail));
		verify(userDao).enable(eq(mail));
	}

	@Test(expected = RedisException.class)
	public void test_enable_RedisException() throws Exception {
		String mail = "mail@mail.com";
		String baseMail = URLCoderUtil.encode(mail);
		String token = String.valueOf(System.currentTimeMillis());
		String baseToken = URLCoderUtil.encode(token);
		AuthenticatingUser au = new AuthenticatingUser(baseMail, baseToken);
		when(cacheService.getEnableToken(eq(mail))).thenReturn(token);
		when(clockService.getCurrentTimeMillis()).thenCallRealMethod();
		when(cacheService.removeAuthToken(eq(mail))).thenReturn(CacheResultEnum.REMOVE_TOKEN_FAILED);
		when(userDao.enable(eq(mail))).thenReturn(1);
		UserServiceResultEnum enableResult = userService.enable(au);
		assertThat(enableResult, is(ENABLE_TOKEN_SUCCESS));
		verify(cacheService).getEnableToken(eq(mail));
		verify(cacheService).removeAuthToken(eq(mail));
		verify(userDao).enable(eq(mail));
	}

	@Test
	public void test_enable_timeout() throws Exception {
		String mail = "mail@mail.com";
		String baseMail = URLCoderUtil.encode(mail);
		long nowToken = System.currentTimeMillis();
		String token = String.valueOf(nowToken);
		String baseToken = URLCoderUtil.encode(token);
		AuthenticatingUser au = new AuthenticatingUser(baseMail, baseToken);
		when(cacheService.getEnableToken(eq(mail))).thenReturn(token);
		when(clockService.getCurrentTimeMillis()).thenReturn(nowToken + 31 * 60 * 1000);
		UserServiceResultEnum enableResult = userService.enable(au);
		assertThat(enableResult, is(ENABLE_TOKEN_TIMEOUT));
		verify(cacheService).getEnableToken(eq(mail));
		verify(clockService).getCurrentTimeMillis();
	}

	@Test
	public void test_enable_invalid() throws Exception {
		String mail = "mail@mail.com";
		String baseMail = URLCoderUtil.encode(mail);
		long nowToken = System.currentTimeMillis();
		String token = String.valueOf(nowToken);
		String baseToken = URLCoderUtil.encode(token);
		AuthenticatingUser au = new AuthenticatingUser(baseMail, baseToken);
		when(cacheService.getEnableToken(eq(mail))).thenReturn(String.valueOf(nowToken + 1));
		UserServiceResultEnum enableResult = userService.enable(au);
		assertThat(enableResult, is(ENABLE_TOKEN_INVALID));
		verify(cacheService).getEnableToken(eq(mail));
	}
}