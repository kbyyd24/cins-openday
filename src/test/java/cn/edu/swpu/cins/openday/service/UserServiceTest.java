package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.dao.persistence.UserDao;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.ADD_USER_USABLE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserDao userDao;

	private UserService userService;

	@Before
	public void setUp() throws Exception {
		userService = new UserServiceImpl(userDao);
	}

	@Test
	public void test_signup_success() throws Exception {
		String username = "username";
		String password = "password";
		String mail = "melo@gaoyuexiang.cn";
		SignUpUser signUpUser =
						new SignUpUser(username, password, password, mail);
		when(userDao.isValidUser(signUpUser)).thenReturn(ADD_USER_USABLE);
		when(userDao.signUpUser(signUpUser)).thenReturn(1);
		UserServiceResultEnum userServiceResultEnum =
						userService.signUp(signUpUser);
		assertThat(userServiceResultEnum, is(UserServiceResultEnum.ADD_USER_SUCCESS));
	}
}