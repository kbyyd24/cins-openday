package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.persistence.Activity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActivityDaoTest {

	@Mock
	private NamedParameterJdbcOperations jdbcOperations;

	private ActivityDao dao;

	@Before
	public void setUp() throws Exception {
		dao = new ActivityDao(jdbcOperations);
	}

	@Test
	public void test_getActivities_success() throws Exception {
		List activities = mock(List.class);
		when(jdbcOperations.query(anyString(), anyMap(), any(RowMapper.class))).thenReturn(activities);
		List<Activity> ret = dao.getActivities(0, 4);
		assertThat(ret, is(activities));
	}

}