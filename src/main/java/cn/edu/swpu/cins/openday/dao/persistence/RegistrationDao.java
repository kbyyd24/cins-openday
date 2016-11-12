package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.persistence.Registration;
import cn.edu.swpu.cins.openday.model.service.TeammateMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class RegistrationDao {
	private static final String INSERT_REGISTRATION =
		"insert into `registration`(match_id, user_id, group_id, captain) " +
			"value (:matchId, :userId, :groupId, :captain)";
	private static final String SELECT_GROUP_ID = "" +
		"SELECT group_id FROM `registration` " +
		"WHERE match_id = :matchId AND user_id = :userId";
	private NamedParameterJdbcOperations jdbcOperations;

	@Autowired
	public RegistrationDao(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public int addRegistration(Registration registration) {
		HashMap<String, Object> insertMap = new HashMap<>();
		insertMap.put("matchId", registration.getMatchId());
		insertMap.put("userId", registration.getUserId());
		insertMap.put("groupId", registration.getGroupId());
		insertMap.put("captain", registration.getCaptain());
		return jdbcOperations.update(INSERT_REGISTRATION, insertMap);
	}

	public Integer getGroupId(int matchId, int userId) {
		HashMap<String, Integer> queryMap = new HashMap<>();
		queryMap.put("matchId", matchId);
		queryMap.put("userId", userId);
		return jdbcOperations.query(
			SELECT_GROUP_ID,
			queryMap,
			rs -> {
			return rs.getInt("group_id");
		});
	}

	public TeammateMsg getTeammateMsg(int matchId, int userId, Integer groupId) {
		return null;
	}
}
