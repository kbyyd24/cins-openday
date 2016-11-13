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
	private static final String SELECT_TEAMMATE_MSG =
		"select user_id, captain from registration " +
		"where match_id = :matchId and group_id = :groupId and user_id != :userId";
	private static final String SELECT_ID =
		"SELECT id FROM registration " +
			"WHERE user_id = " +
			"(SELECT id FROM user WHERE mail = :mail)";
	private static final String SELECT_REGISTRATION =
		"select id from registration " +
			"where user_id = :userId and match_id = :matchId";
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

	public TeammateMsg getTeammateMsg(int matchId, int userId, int groupId) {
		HashMap<String, Integer> queryMap = new HashMap<>();
		queryMap.put("matchId", matchId);
		queryMap.put("userId", userId);
		queryMap.put("groupId", groupId);
		return jdbcOperations.query(
			SELECT_TEAMMATE_MSG,
			queryMap,
			rs -> {return new TeammateMsg(
				rs.getInt("user_id"),
				rs.getBoolean("captain"));
			});
	}

	public Registration getRegistration(int matchId, int userId) {
		HashMap<String, Integer> queryMap = new HashMap<>();
		queryMap.put("matchId", matchId);
		queryMap.put("userId", userId);
		return jdbcOperations.query(
			SELECT_REGISTRATION,
			queryMap,
			rs -> {
				return new Registration(rs.getInt("id"));
			});
	}
}
