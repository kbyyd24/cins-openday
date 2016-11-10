package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.persistence.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class RegistrationDao {
	private static final String INSERT_REGISTRATION =
		"insert into `registration`(match_id, user_id, group_id) " +
			"value (:matchId, :userId, :groupId)";
	private NamedParameterJdbcOperations jdbcOperations;

	@Autowired
	public RegistrationDao(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public int addRegistration(Registration registration) {
		HashMap<String, Integer> insertMap = new HashMap<>();
		insertMap.put("matchId", registration.getMatchId());
		insertMap.put("userId", registration.getUserId());
		insertMap.put("groupId", registration.getGroupId());
		return jdbcOperations.update(INSERT_REGISTRATION, insertMap);
	}
}