package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.http.UpMatch;
import cn.edu.swpu.cins.openday.model.persistence.Match;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.eq;
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
	public void test_addMatch_success() throws Exception {
		UpMatch upMatch = new UpMatch();
		String sql = "INSERT INTO `match`(`match_name`, `detail`, `start_time`, `end_time`) " +
			"VALUE (:matchName, :detail, :startTime, :endTime)";
		when(jdbcOperations.update(eq(sql), anyMap())).thenReturn(1);
		int line = dao.addMatch(upMatch);
		assertThat(line, is(1));
		verify(jdbcOperations).update(eq(sql), anyMap());
	}

	@Test
	public void test_getMatches_success() throws Exception {
		int offset = 4;
		int limit = 0;
		String sql = "SELECT `id`, `match_name`, `detail`, `start_time`, `end_time` " +
			"FROM `match` LIMIT :limit, :offset";
		List matches = mock(List.class);
		when(jdbcOperations.query(eq(sql), anyMap(), any(RowMapper.class))).thenReturn(matches);
		List<Match> ret = dao.getMatches(limit, offset);
		assertThat(ret, is(matches));
		verify(jdbcOperations).query(eq(sql), anyMap(), any(RowMapper.class));
	}
}