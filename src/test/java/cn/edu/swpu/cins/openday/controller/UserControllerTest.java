package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.http.UserHttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.http.UserHttpResult;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private MailService mailService;

	private UserController userController;

	@Before
	public void setUp() throws Exception {
		userController = new UserController(userService, mailService);
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
		doNothing().when(mailService).send(mail, subject, text);
		UserHttpResult httpResult = userController.signUp(signUpUser);
		assertThat(httpResult.getCode(), is(UserHttpResultEnum.ADD_USER_SUCCESS.getCode()));
	}
}