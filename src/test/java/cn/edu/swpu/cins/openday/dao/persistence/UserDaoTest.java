package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.ArrayList;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.ADD_USER_USABLE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {

	@Mock
	private NamedParameterJdbcOperations jdbcOperations;

	private UserDao userDao;

	@Before
	public void setUp() throws Exception {
		userDao = new UserDao(jdbcOperations);
	}

	@Test
	public void test_checkNewUser_usable() throws Exception {
		String username = "username";
		String password = "password";
		String mail = "melo@gaoyuexiang.cn";
		SignUpUser signUpUser = new SignUpUser(username, password, password, mail);
		when(jdbcOperations.query(anyString(),
						anyMapOf(String.class, String.class),
						any(RowMapper.class)))
						.thenReturn(new ArrayList<>());
		UserServiceResultEnum ret = userDao.checkNewUser(signUpUser);
		assertThat(ret, is(ADD_USER_USABLE));
	}

	@Test
	public void test_signUpUser_success() throws Exception {
		String username = "username";
		String password = "password";
		String mail = "melo@gaoyuexiang.cn";
		SignUpUser signUpUser = new SignUpUser(username, password, password, mail);
		when(jdbcOperations.update(anyString(),
						anyMapOf(String.class, String.class)))
						.thenReturn(1);
		int ret = userDao.signUpUser(signUpUser);
		assertThat(ret, is(1));
	}
}