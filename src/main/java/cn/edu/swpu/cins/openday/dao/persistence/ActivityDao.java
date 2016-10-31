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
		"SELECT id, title, content, img, create_time, end_time " +
			"FROM activity LIMIT :limit, :offset";
	private final NamedParameterJdbcOperations jdbcOperations;

	@Autowired
	public ActivityDao(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public List<Activity> getActivities(int limit, int offset) {
		HashMap<String, String> queryMap = new HashMap<>();
		queryMap.put("limit", String.valueOf(limit));
		queryMap.put("offset", String.valueOf(offset));
		return jdbcOperations.query(
			QUERY_ACTIVITIES,
			queryMap,
			((rs, rowNum) -> new Activity(
				rs.getInt("id"),
				rs.getString("title"),
				rs.getString("content"),
				rs.getString("img"),
				rs.getLong("create_time"),
				rs.getLong("end_time")
			)));
	}
}
