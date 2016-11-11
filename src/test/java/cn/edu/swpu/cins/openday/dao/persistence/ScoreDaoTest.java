package cn.edu.swpu.cins.openday.dao.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScoreDaoTest {

	@Mock
	private JdbcOperations jdbcOperations;

	private ScoreDao dao;

	@Before
	public void setUp() throws Exception {
		dao = new ScoreDao(jdbcOperations);
	}

	@Test
	public void test_getAll_success() throws Exception {
		List scores = mock(List.class);
		when(jdbcOperations.query(anyString(), any(RowMapper.class))).thenReturn(scores);
		assertThat(dao.getAll(), is(scores));
		verify(jdbcOperations).query(anyString(), any(RowMapper.class));
	}
}