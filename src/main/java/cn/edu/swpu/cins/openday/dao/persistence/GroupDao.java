package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.persistence.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class GroupDao {
	private static final String INSERT_GROUP =
		"insert into `group` (group_name, match_id) " +
			"values (:groupName, :matchId);";
	private static final String SELECT_GROUP_ID =
		"select id from `group` " +
			"where group_name = :groupName and match_id = :matchId";
	private NamedParameterJdbcOperations jdbcOperations;

	@Autowired
	public GroupDao(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public int addGroup(Group group) {
		HashMap<String, Object> insertMap = new HashMap<>();
		insertMap.put("groupName", group.getGroupName());
		insertMap.put("matchId", group.getMatchId());
		return jdbcOperations.update(INSERT_GROUP, insertMap);
	}

	public Integer getGroupId(Group group) {
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("groupName", group.getGroupName());
		queryMap.put("matchId", group.getMatchId());
		return jdbcOperations.query(
			SELECT_GROUP_ID,
			queryMap,
			rs -> {
				return rs.getInt("id");
			}
		);
	}

	public String getGroupName(int groupId) {
		return null;
	}
}
