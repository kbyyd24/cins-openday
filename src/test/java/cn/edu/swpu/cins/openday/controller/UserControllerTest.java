package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.http.UserHttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.http.UserHttpResult;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;
import cn.edu.swpu.cins.openday.service.MailFormatService;
import cn.edu.swpu.cins.openday.service.MailService;
import cn.edu.swpu.cins.openday.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private MailService mailService;

	@Mock
	private MailFormatService mailFormatService;

	private UserController userController;

	@Before
	public void setUp() throws Exception {
		userController = new UserController(userService, mailService, mailFormatService);
	}

	@Test
	public void test_sign_up_user() throws Exception {
		String username = "username";
		String password = "password";
		String repassword = "password";
		String mail = "melo@gaoyuexiang.cn";
		SignUpUser signUpUser = new SignUpUser(username, password, repassword, mail);
		when(userService.signUp(eq(signUpUser), anyString())).thenReturn(UserServiceResultEnum.ADD_USER_SUCCESS);
		String subject = "subject";
		String text = "text";
		when(mailFormatService.getSignUpSubject(username)).thenReturn(subject);
		when(mailFormatService.getSignUpContent(eq(mail), anyString())).thenReturn(text);
		doNothing().when(mailService).send(mail, subject, text);
		UserHttpResult httpResult = userController.signUp(signUpUser);
		assertThat(httpResult.getCode(), is(UserHttpResultEnum.ADD_USER_SUCCESS.getCode()));
	}

	@Test
	public void test_enable_success() throws Exception {
		String baseMail = "mail@mail.com";
		String baseToken = "123";
		AuthenticatingUser au = new AuthenticatingUser(baseMail, baseToken);
		when(userService.enable(au)).thenReturn(UserServiceResultEnum.ENABLE_TOKEN_SUCCESS);
		UserHttpResult ret = userController.enable(au);
		assertThat(ret.getCode(), is(UserHttpResultEnum.ENABLE_TOKEN_SUCCESS.getCode()));
	}

	@Test
	public void test_login_success() throws Exception {
		String mail = "melo@gaoyuexiang.cn";
		String password = "MambaOut";
		SignInUser signInUser = new SignInUser(mail, password);
		when(userService.signin(signInUser)).thenReturn(UserServiceResultEnum.LOGIN_SUCCESS);
		UserHttpResult httpResult = userController.signIn(signInUser);
		verify(userService).signin(signInUser);
		assertThat(httpResult.getCode(), is(UserHttpResultEnum.LOGIN_SUCCESS.getCode()));
	}

	@Test
	public void test_login_failed() throws Exception {
		String mail = "melo@gaoyuexiang.cn";
		String password = "MambaOut";
		SignInUser signInUser = new SignInUser(mail, password);
		when(userService.signin(signInUser)).thenReturn(UserServiceResultEnum.LOGIN_FAILED);
		UserHttpResult httpResult = userController.signIn(signInUser);
		verify(userService).signin(signInUser);
		assertThat(httpResult.getCode(), is(UserHttpResultEnum.LOGIN_FAILED.getCode()));
	}
}