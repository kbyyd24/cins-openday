package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.persistence.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.ArrayList;
import java.util.List;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.ADD_USER_USABLE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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

	@Test
	public void test_signIn_success() throws Exception {
		String mail = "melo@gaoyuexiang.cn";
		String password = "MambaOut";
		SignInUser signInUser = new SignInUser(mail, password);
		int id = 1;
		String username = "kb永远的24";
		User user = new User(id, username, mail, password, true);
		when(jdbcOperations.query(anyString(),
			anyMapOf(String.class, String.class),
			any(ResultSetExtractor.class)))
			.thenReturn(user);
		assertThat(userDao.signInUser(signInUser), is(user));
	}

	@Test
	public void test_getId_success() throws Exception {
		List users = mock(List.class);
		when(jdbcOperations.query(anyString(), anyMap(), any(RowMapper.class))).thenReturn(users);
		String mail1 = "mail";
		List<User> ret = userDao.getIds(mail1, mail1);
		assertThat(ret, is(users));
		verify(jdbcOperations).query(anyString(), anyMap(), any(RowMapper.class));
	}

	@Test
	public void test_getTeammateMsg_success() throws Exception {
		List<Object> users = mock(List.class);
		when(jdbcOperations.query(anyString(), anyMap(), any(RowMapper.class))).thenReturn(users);
		int id1 = 1;
		int id2 = 1;
		assertThat(userDao.getTeammateMsgs(id1, id2), is(users));
		verify(jdbcOperations).query(anyString(), anyMap(), any(RowMapper.class));
	}
}