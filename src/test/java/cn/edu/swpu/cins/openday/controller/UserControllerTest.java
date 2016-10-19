package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.model.http.User;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;
import cn.edu.swpu.cins.openday.service.CacheService;
import cn.edu.swpu.cins.openday.service.MailService;
import cn.edu.swpu.cins.openday.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.ADD_AUTHENTICATING_USER_SUCCESS;
import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.ADD_USER_SUCCESS;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private MailService mailService;

	@Mock
	private CacheService cacheService;

	private UserController userController;

	@Before
	public void setUp() throws Exception {
		userController = new UserController(userService, mailService, cacheService);
	}

	@Test
	public void test_sign_up_user() throws Exception {
		String username = "username";
		String password = "password";
		String repassword = "password";
		String mail = "melo@gaoyuexiang.cn";
		User user = new User(username, password, repassword, mail);
		when(userService.signUp(user)).thenReturn(ADD_USER_SUCCESS);
		when(cacheService.saveAuthingUser(any(AuthenticatingUser.class)))
						.thenReturn(ADD_AUTHENTICATING_USER_SUCCESS);
		String subject = "subject";
		String text = "text";
		doNothing().when(mailService).send(mail, subject, text);
		userController.signUp(user);
	}
}