package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.model.http.PostActivity;
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
	private static final String ADD_ACTIVITY =
		"INSERT INTO activity(title, content, img, create_time, end_time) " +
			"VALUE (:title, :content, :img, :createTime, :endTime)";
	private final NamedParameterJdbcOperations jdbcOperations;

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
				rs.getString("content"),
				rs.getString("img"),
				rs.getLong("create_time"),
				rs.getLong("end_time")
			)));
	}

	public int addActivity(PostActivity postActivity) {
		HashMap<String, Object> insertMap = new HashMap<>();
		insertMap.put("title", postActivity.getTitle());
		insertMap.put("content", postActivity.getContent());
		insertMap.put("img", postActivity.getImg());
		insertMap.put("startTime", postActivity.getStartTime());
		insertMap.put("endTime", postActivity.getEndTime());
		return jdbcOperations.update(ADD_ACTIVITY, insertMap);
	}
}