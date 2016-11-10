package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.persistence.Registration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
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
}