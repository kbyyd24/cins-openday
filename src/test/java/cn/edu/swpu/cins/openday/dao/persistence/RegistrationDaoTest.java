package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.persistence.Registration;
import cn.edu.swpu.cins.openday.model.service.TeammateMsg;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationDaoTest {
	@Mock
	private NamedParameterJdbcOperations jdbcOperations;

	private RegistrationDao dao;

	@Before
	public void setUp() throws Exception {
		dao = new RegistrationDao(jdbcOperations);
	}

	@Test
	public void test_addRegistration_success() throws Exception {
		Registration registration = new Registration();
		when(jdbcOperations.update(anyString(), anyMap())).thenReturn(1);
		int line = dao.addRegistration(registration);
		assertThat(line, is(1));
		verify(jdbcOperations).update(anyString(), anyMap());
	}

	@Test
	public void test_getGroupId_success() throws Exception {
		int userId = 1;
		int matchId = 1;
		int groupId = 1;
		when(jdbcOperations.query(anyString(), anyMap(), any(ResultSetExtractor.class))).thenReturn(groupId);
		assertThat(dao.getGroupId(matchId, userId), is(groupId));
		verify(jdbcOperations).query(anyString(), anyMap(), any(ResultSetExtractor.class));
	}

	@Test
	public void test_getTeammateMsg_success() throws Exception {
		TeammateMsg tm = mock(TeammateMsg.class);
		when(jdbcOperations.query(anyString(), anyMap(), any(ResultSetExtractor.class))).thenReturn(tm);
		int matchId = 1;
		int userId = 1;
		int groupId = 1;
		assertThat(dao.getTeammateMsg(matchId, userId, groupId), is(tm));
		verify(jdbcOperations).query(anyString(), anyMap(), any(ResultSetExtractor.class));
	}

	@Test
	public void test_getRegistration_success() throws Exception {
		int userId = 1;
		int matchId = 1;
		Registration registration = mock(Registration.class);
		when(jdbcOperations.query(anyString(), anyMap(), any(ResultSetExtractor.class))).thenReturn(registration);
		assertThat(dao.getRegistration(matchId, userId), is(registration));
		verify(jdbcOperations).query(anyString(), anyMap(), any(ResultSetExtractor.class));
	}
}