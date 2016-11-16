package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.UserDao;
import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.exception.NoUserToEnableException;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.model.persistence.User;
import cn.edu.swpu.cins.openday.model.service.AuthUser;
import cn.edu.swpu.cins.openday.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserDao userDao;

	@Mock
	private CacheService cacheService;

	@Mock
	private TimeServiceImpl clockService;

	@Mock
	private PasswordEncoderService passwordService;

	@Mock
	private URLCoderService urlCoderService;

	@Mock
	private TokenService tokenService;

	private UserService userService;

	@Before
	public void setUp() throws Exception {
		userService = new UserServiceImpl(userDao, cacheService, clockService, passwordService, urlCoderService, tokenService);
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
		AuthUser au = new AuthUser(mail, token);
		when(cacheService.saveAuthingUser(au)).thenReturn(CacheResultEnum.SAVE_SUCCESS);
		UserServiceResultEnum userServiceResultEnum =
						userService.signUp(signUpUser, token);
		assertThat(userServiceResultEnum, is(UserServiceResultEnum.ADD_USER_SUCCESS));
	}

	@Test
	public void test_enable_success() throws Exception {
		String mail = "mail@mail.com";
		String token = String.valueOf(System.currentTimeMillis());
		AuthUser au = new AuthUser(mail, token);
		when(urlCoderService.decode(mail)).thenReturn(mail);
		when(urlCoderService.decode(token)).thenReturn(token);
		when(cacheService.getEnableToken(eq(mail))).thenReturn(token);
		when(clockService.getCurrentTimeMillis()).thenCallRealMethod();
		when(userDao.enable(eq(mail))).thenReturn(1);
		UserServiceResultEnum enableResult = userService.enable(au);
		assertThat(enableResult, is(ENABLE_TOKEN_SUCCESS));
		verify(cacheService).getEnableToken(eq(mail));
		verify(userDao).enable(eq(mail));
	}

	@Test(expected = NoUserToEnableException.class)
	public void test_enable_NoUserToEnableException() throws Exception {
		String mail = "mail@mail.com";
		String token = String.valueOf(System.currentTimeMillis());
		AuthUser au = new AuthUser(mail, token);
		when(urlCoderService.decode(mail)).thenReturn(mail);
		when(urlCoderService.decode(token)).thenReturn(token);
		when(cacheService.getEnableToken(eq(mail))).thenReturn(token);
		when(clockService.getCurrentTimeMillis()).thenCallRealMethod();
		when(userDao.enable(eq(mail))).thenReturn(0);
		UserServiceResultEnum enableResult = userService.enable(au);
		assertThat(enableResult, is(ENABLE_TOKEN_SUCCESS));
		verify(cacheService).getEnableToken(eq(mail));
		verify(userDao).enable(eq(mail));
	}

	@Test
	public void test_enable_timeout() throws Exception {
		String mail = "mail@mail.com";
		long nowToken = System.currentTimeMillis();
		String token = String.valueOf(nowToken);
		AuthUser au = new AuthUser(mail, token);
		when(urlCoderService.decode(mail)).thenReturn(mail);
		when(urlCoderService.decode(token)).thenReturn(token);
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
		long nowToken = System.currentTimeMillis();
		String token = String.valueOf(nowToken);
		AuthUser au = new AuthUser(mail, token);
		when(urlCoderService.decode(mail)).thenReturn(mail);
		when(urlCoderService.decode(token)).thenReturn(token);
		when(cacheService.getEnableToken(eq(mail))).thenReturn(String.valueOf(nowToken + 1));
		UserServiceResultEnum enableResult = userService.enable(au);
		assertThat(enableResult, is(ENABLE_TOKEN_INVALID));
		verify(cacheService).getEnableToken(eq(mail));
	}

	@Test
	public void test_signin_success() throws Exception {
		String mail = "melo@gaoyuexiang.cn";
		String password = "MambaOut";
		SignInUser signInUser = new SignInUser(mail, password);
		int id = 1;
		String username = "kb永远的24";
		User user = new User(id, username, mail, password, true);
		when(userDao.signInUser(signInUser)).thenReturn(user);
		when(passwordService.match(password, password)).thenReturn(true);
		when(tokenService.createToken()).thenReturn(mail);
		when(cacheService.signIn(any(UserSignInResult.class))).thenReturn(CacheResultEnum.SAVE_SUCCESS);
		UserSignInResult result = userService.signin(signInUser);
		assertThat(result.getId(), is(id));
		assertThat(result.getUsername(), is(username));
		assertThat(result.getMail(), is(mail));
		assertThat(result.getStatus(), is(UserServiceResultEnum.LOGIN_SUCCESS.name()));
		verify(userDao).signInUser(signInUser);
		verify(passwordService).match(password, password);
		verify(cacheService).signIn(any(UserSignInResult.class));
	}

	@Test
	public void test_signIn_user_not_exist() throws Exception {
		String mail = "melo@gaoyuexiang.cn";
		String password = "MambaOut";
		SignInUser signInUser = new SignInUser(mail, password);
		when(userDao.signInUser(signInUser)).thenReturn(null);
		UserSignInResult result = userService.signin(signInUser);
		assertThat(result.getStatus(), is(UserServiceResultEnum.USER_NOT_EXIST.name()));
		verify(userDao).signInUser(signInUser);
		verify(passwordService, times(0)).match(password, password);
	}

	@Test
	public void test_signIn_user_password_error() throws Exception {
		String mail = "melo@gaoyuexiang.cn";
		String password = "MambaOut";
		SignInUser signInUser = new SignInUser(mail, password);
		int id = 1;
		String username = "kb永远的24";
		User user = new User(id, username, mail, password, true);
		when(userDao.signInUser(signInUser)).thenReturn(user);
		when(passwordService.match(password, password)).thenReturn(false);
		assertThat(userService.signin(signInUser).getStatus(), is(UserServiceResultEnum.PASSWORD_NOT_SAME.name()));
		verify(userDao).signInUser(signInUser);
		verify(passwordService).match(password, password);
		verify(cacheService, times(0)).signIn(any(UserSignInResult.class));
	}

	@Test
	public void test_signIn_cache_failed() throws Exception {
		String mail = "melo@gaoyuexiang.cn";
		String password = "MambaOut";
		SignInUser signInUser = new SignInUser(mail, password);
		int id = 1;
		String username = "kb永远的24";
		User user = new User(id, username, mail, password, true);
		when(userDao.signInUser(signInUser)).thenReturn(user);
		when(passwordService.match(password, password)).thenReturn(true);
		when(tokenService.createToken()).thenReturn(mail);
		when(cacheService.signIn(any(UserSignInResult.class))).thenReturn(CacheResultEnum.SAVE_FAILED);
		assertThat(userService.signin(signInUser).getStatus(), is(UserServiceResultEnum.CACHE_FAILED.name()));
		verify(userDao).signInUser(signInUser);
		verify(passwordService).match(password, password);
		verify(cacheService).signIn(any(UserSignInResult.class));
	}

	@Test
	public void test_signOut_success() throws Exception {
		String mail = "mail@mail.com";
		String token = "123";
		AuthUser au = new AuthUser(mail, token);
		when(cacheService.signout(au)).thenReturn(CacheResultEnum.REMOVE_SUCCESS);
		UserServiceResultEnum result = userService.signOut(au);
		assertThat(result, is(UserServiceResultEnum.SIGNOUT_SUCCESS));
		verify(cacheService).signout(au);
	}

	@Test
	public void test_signOut_failed() throws Exception {
		String mail = "mail@mail.com";
		String token = "123";
		AuthUser au = new AuthUser(mail, token);
		when(cacheService.signout(au)).thenReturn(CacheResultEnum.REMOVE_FAILED);
		UserServiceResultEnum result = userService.signOut(au);
		assertThat(result, is(UserServiceResultEnum.SIGNOUT_FAILED));
		verify(cacheService).signout(au);
	}
}