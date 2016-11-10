package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.http.UpMatch;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class MatchDao {
	private static final String ADD_MATCH =
		"INSERT INTO `match`(`match_name`, `detail`, `start_time`, `end_time`) " +
			"VALUE (:matchName, :detail, :startTime, :endTime)";
	private static final String SELECT_MATCHES =
		"SELECT `id`, `match_name`, `detail`, `start_time`, `end_time` " +
			"FROM `match` LIMIT :limit, :offset";
	private NamedParameterJdbcOperations jdbcOperations;

	@Autowired
	public MatchDao(NamedParameterJdbcOperations jdbcOperations) {
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
		HashMap<String, Integer> queryMap = new HashMap<>();
		queryMap.put("limit", limit);
		queryMap.put("offset", offset);
		return jdbcOperations.query(
			SELECT_MATCHES,
			queryMap,
			(rs, rowNum) -> new Match(
				rs.getInt("id"),
				rs.getString("match_name"),
				rs.getString("detail"),
				rs.getLong("start_time"),
				rs.getLong("end_time")
			));
	}

	public Match getDataSet(int id) {
		return null;
	}
}
