package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.persistence.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class GroupDao {
	private static final String INSERT_GROUP =
		"insert ignore into `group` (group_name, match_id) " +
			"values (:groupName, :matchId);";
	private static final String SELECT_GROUP_ID =
		"select id from `group` " +
			"where group_name = :groupName and match_id = :matchId";
	private static final String SELECT_GROUP_NAME =
		"select group_name from `group` " +
			"where id = :groupId";
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

	public Group getGroupId(Group group) {
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("groupName", group.getGroupName());
		queryMap.put("matchId", group.getMatchId());
		return jdbcOperations.query(
			SELECT_GROUP_ID,
			queryMap,
			rs -> {
				if (rs.next()) {
					return new Group(rs.getInt("id"));
				}
				return new Group();
			}
		);
	}

	public String getGroupName(int groupId) {
		HashMap<String, Integer> queryMap = new HashMap<>();
		queryMap.put("groupId", groupId);
		return jdbcOperations.query(
			SELECT_GROUP_NAME,
			queryMap,
			rs -> {
				if (rs.next()) {
					return rs.getString("group_name");
				}
				return null;
			});
	}
}
