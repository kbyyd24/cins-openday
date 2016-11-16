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
		"INSERT INTO `match`(`match_name`, `detail`, `start_time`, `end_time`, `judge_standard`, `commit_regular`, `award`) " +
			"VALUE (:matchName, :detail, :startTime, :endTime, :judgeStandard, :commitRegular, :award)";
	private static final String SELECT_MATCHES =
		"SELECT `id`, `match_name`, `detail`, `start_time`, `end_time` " +
			"FROM `match` LIMIT :limit, :offset";
	private static final String SELECT_DATA_SET =
		"select `data_link`, `data_password` " +
			"from `match` where `id` = :id";
	private static final String SELECT_MATCH =
		"select `id`, `match_name`, `detail`, `time_plan`, `award`, `judge_standard`, `commit_regular` " +
			"from `match`";
	private NamedParameterJdbcOperations jdbcOperations;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	public MatchDao(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	@Deprecated
	public int addMatch(UpMatch upMatch) {
		HashMap<String, Object> insertMap = new HashMap<>();
		insertMap.put("matchName", upMatch.getMatchName());
		insertMap.put("detail", upMatch.getDetail());
		insertMap.put("startTime", upMatch.getStartTime());
		insertMap.put("endTime", upMatch.getEndTime());
		insertMap.put("judgeStandard", upMatch.getJudgeStandard());
		insertMap.put("commitRegular", upMatch.getCommitRegular());
		insertMap.put("award", upMatch.getAward());
		return jdbcOperations.update(ADD_MATCH, insertMap);
	}

	@Deprecated
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

	public Match getMatch() {
		return jdbcOperations.query(
			SELECT_MATCH,
			rs -> {
				if (rs.next()) {
					return new Match(
						rs.getInt("id"),
						rs.getString("match_name"),
						rs.getString("detail"),
						rs.getString("time_plan"),
						rs.getString("award"),
						rs.getString("judge_standard"),
						rs.getString("commit_regular")
					);
				}
				return new Match();
		});
	}

	public Match getDataSet(int id) {
		HashMap<String, Integer> queryMap = new HashMap<>();
		queryMap.put("id", id);
		return jdbcOperations.query(
			SELECT_DATA_SET,
			queryMap,
			rs -> {
			return new Match(rs.getString("data_link"), rs.getString("data_password"));
		});
	}
}
