package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.HttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.HttpResult;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.model.service.AuthUser;
import cn.edu.swpu.cins.openday.service.TimeService;
import cn.edu.swpu.cins.openday.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private TimeService timeService;

	private UserController userController;

	@Before
	public void setUp() throws Exception {
		userController = new UserController(userService, timeService);
	}

	@Test
	public void test_sign_up_user() throws Exception {
		HttpResult httpResult = mock(HttpResult.class);
		SignUpUser signUpUser = mock(SignUpUser.class);
		Long tokenLong = 1L;
		when(timeService.getCurrentTimeMillis()).thenReturn(tokenLong);
		String token = "1";
		when(userService.signUp(eq(signUpUser), eq(token))).thenReturn(httpResult);
		when(httpResult.getCode()).thenReturn(HttpResultEnum.SIGN_UP_USER_SUCCESS.getCode());
		HttpResult ret = userController.signUp(signUpUser);
		assertThat(ret.getCode(), is(HttpResultEnum.SIGN_UP_USER_SUCCESS.getCode()));
		verify(timeService).getCurrentTimeMillis();
		verify(userService).signUp(eq(signUpUser), eq(token));
		verify(httpResult).getCode();
	}

	@Test
	public void test_enable_success() throws Exception {
		String baseMail = "mail@mail.com";
		String baseToken = "123";
		AuthUser au = new AuthUser(baseMail, baseToken);
		when(userService.enable(au)).thenReturn(UserServiceResultEnum.ENABLE_TOKEN_SUCCESS);
		HttpResult ret = userController.enable(au);
		assertThat(ret.getCode(), is(HttpResultEnum.ENABLE_TOKEN_SUCCESS.getCode()));
	}

	@Test
	public void test_login_success() throws Exception {
		String mail = "melo@gaoyuexiang.cn";
		String password = "MambaOut";
		SignInUser signInUser = new SignInUser(mail, password);
		UserSignInResult userSignInResult = new UserSignInResult(UserServiceResultEnum.LOGIN_SUCCESS);
		when(userService.signin(signInUser)).thenReturn(userSignInResult);
		UserSignInResult result = userController.signIn(signInUser);
		assertThat(result, is(userSignInResult));
		verify(userService).signin(signInUser);
	}

	@Test
	public void test_login_failed() throws Exception {
		String mail = "melo@gaoyuexiang.cn";
		String password = "MambaOut";
		SignInUser signInUser = new SignInUser(mail, password);

		UserSignInResult userSignInResult = new UserSignInResult(UserServiceResultEnum.USER_NOT_EXIST);
		when(userService.signin(signInUser)).thenReturn(userSignInResult);
		assertThat(userController.signIn(signInUser).getStatus(), is(UserServiceResultEnum.USER_NOT_EXIST.name()));

		userSignInResult = new UserSignInResult(UserServiceResultEnum.PASSWORD_NOT_SAME);
		when(userService.signin(signInUser)).thenReturn(userSignInResult);
		assertThat(userController.signIn(signInUser).getStatus(), is(UserServiceResultEnum.PASSWORD_NOT_SAME.name()));

		userSignInResult = new UserSignInResult(UserServiceResultEnum.CACHE_FAILED);
		when(userService.signin(signInUser)).thenReturn(userSignInResult);
		assertThat(userController.signIn(signInUser).getStatus(), is(UserServiceResultEnum.CACHE_FAILED.name()));
	}

	@Test
	public void test_signOut_success() throws Exception {
		String mail = "mail@mail.com";
		String token = "123";
		AuthUser au = new AuthUser(mail, token);
		when(userService.signOut(au)).thenReturn(UserServiceResultEnum.SIGN_OUT_SUCCESS);
		HttpResult result = userController.signOut(au);
		assertThat(result.getCode(), is(HttpResultEnum.SIGN_OUT_SUCCESS.getCode()));
		verify(userService).signOut(au);
	}
}