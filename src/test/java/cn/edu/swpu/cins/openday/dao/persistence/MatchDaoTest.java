package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.persistence.Match;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MatchDaoTest {

	@Mock
	private NamedParameterJdbcOperations jdbcOperations;

	private MatchDao dao;

	@Before
	public void setUp() throws Exception {
		dao = new MatchDao(jdbcOperations);
	}

	@Test
	public void test_getMatch_success() throws Exception {
		Match match = mock(Match.class);
		when(jdbcOperations.query(anyString(), any(ResultSetExtractor.class))).thenReturn(match);
		Match ret = dao.getMatch();
		assertThat(ret, is(match));
		verify(jdbcOperations).query(anyString(), any(ResultSetExtractor.class));
	}

	@Test
	public void test_getDataSet_success() throws Exception {
		int id = 1;
		Match match = mock(Match.class);
		when(jdbcOperations.query(anyString(), anyMap(), any(ResultSetExtractor.class))).thenReturn(match);
		Match dataSet = dao.getDataSet(id);
		assertThat(dataSet, is(match));
		verify(jdbcOperations).query(anyString(), anyMap(), any(ResultSetExtractor.class));
	}
}