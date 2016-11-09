package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.http.UpMatch;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class MatchDao {
	private static final String ADD_MATCH =
		"INSERT INTO `match`(`match_name`, `detail`, `start_time`, `end_time`) " +
			"VALUE (:matchName, :detail, :startTime, :endTime)";
	private JdbcOperations jdbcOperations;

	@Autowired
	public MatchDao(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public int addMatch(UpMatch upMatch) {
		HashMap<String, Object> insertMap = new HashMap<>();
		insertMap.put("matchName", upMatch.getMatchName());
		insertMap.put("detail", upMatch.getDetail());
		insertMap.put("startTime", upMatch.getStartTime());
		insertMap.put("endTime", upMatch.getEndTime());
		return jdbcOperations.update(ADD_MATCH, insertMap);
	}

	public List<Match> getMatches(int limit, int offset) {
		return null;
	}
}
