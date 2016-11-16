package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.persistence.Group;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
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

	@Test
	public void test_getGroupId_success() throws Exception {
		Group group = new Group();
		int id = 1;
		group.setId(id);
		when(jdbcOperations.query(anyString(), anyMap(), any(ResultSetExtractor.class))).thenReturn(group);
		int groupId = dao.getGroupId(group).getId();
		assertThat(groupId, is(id));
		verify(jdbcOperations).query(anyString(), anyMap(), any(ResultSetExtractor.class));
	}

	@Test
	public void test_getGroupName_success() throws Exception {
		String name = "group name";
		when(jdbcOperations.query(anyString(), anyMap(), any(ResultSetExtractor.class))).thenReturn(name);
		int groupId = 1;
		assertThat(dao.getGroupName(groupId), is (name));
		verify(jdbcOperations).query(anyString(), anyMap(), any(ResultSetExtractor.class));
	}
}