package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.persistence.Group;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroupDaoTest {

	@Mock
	private NamedParameterJdbcOperations jdbcOperations;

	private GroupDao dao;

	@Before
	public void setUp() throws Exception {
		dao = new GroupDao(jdbcOperations);
	}

	@Test
	public void test_addGroup_success() throws Exception {
		Group group = new Group();
		String sql = "insert into `group` (group_name, match_id) " +
			"values (:groupName, :matchId);";
		when(jdbcOperations.update(eq(sql), anyMap())).thenReturn(1);
		int line = dao.addGroup(group);
		assertThat(line, is(1));
		verify(jdbcOperations).update(eq(sql), anyMap());
	}
}