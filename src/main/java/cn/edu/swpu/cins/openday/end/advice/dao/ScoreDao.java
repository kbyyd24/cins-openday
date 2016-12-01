package cn.edu.swpu.cins.openday.end.advice.dao;

import cn.edu.swpu.cins.openday.end.advice.model.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScoreDao {

	private static final String SELECT_GROUP_ID =
		"select group_id from score order by score desc";
	private NamedParameterJdbcOperations jdbcOperations;

	@Autowired
	public ScoreDao(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public List<Rank> getRank() {
		return jdbcOperations.query(
			SELECT_GROUP_ID,
			(rs, rowNum) ->
				new Rank(rs.getInt("group_id")));
	}

}
