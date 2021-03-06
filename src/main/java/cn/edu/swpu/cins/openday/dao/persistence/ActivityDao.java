package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.persistence.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ActivityDao {
	private static final String QUERY_ACTIVITIES =
		"SELECT id, title, content " +
			"FROM activity LIMIT :limit, :offset";
	private final NamedParameterJdbcOperations jdbcOperations;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	public ActivityDao(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public List<Activity> getActivities(int limit, int offset) {
		HashMap<String, Integer> queryMap = new HashMap<>();
		queryMap.put("limit", limit);
		queryMap.put("offset", offset);
		return jdbcOperations.query(
			QUERY_ACTIVITIES,
			queryMap,
			((rs, rowNum) -> new Activity(
				rs.getInt("id"),
				rs.getString("title"),
				rs.getString("content")
			)));
	}

}
