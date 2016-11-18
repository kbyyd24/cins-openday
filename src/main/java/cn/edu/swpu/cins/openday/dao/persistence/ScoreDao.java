package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.service.ScoreRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScoreDao {
	private static final String SELECT_ALL_WITH_NAME =
		"SELECT g.group_name as groupName, r.group_id as groupId, s.score as score, s.time as time " +
			"FROM `core` AS s " +
			"INNER JOIN `registration` AS r ON (s.regist_id = r.id) " +
			"INNER JOIN `group` AS g ON (r.group_id = g.id)";
	private JdbcOperations jdbcOperations;

	@Autowired
	public ScoreDao(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public List<ScoreRank> getAll() {
		return jdbcOperations.query(
			SELECT_ALL_WITH_NAME,
			(rs, row) -> new ScoreRank(
				rs.getString("groupName"),
				rs.getInt("groupId"),
				rs.getInt("score"),
				rs.getLong("time")
			));
	}
}
