package cn.edu.swpu.cins.openday.end.advice.dao;

import cn.edu.swpu.cins.openday.end.advice.model.Regist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

	private static final String SELECT_MAIL_AND_GROUP_ID =
		"select u.mail as mail, r.group_id as groupId " +
			"from user as u " +
			"inner join registration as r " +
			"on r.user_id = u.id";
	private NamedParameterJdbcOperations jdbcOperations;

	@Autowired
	public UserDao(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public List<Regist> getMails() {
		return jdbcOperations.query(
			SELECT_MAIL_AND_GROUP_ID,
			(rs, rowNum) ->
				new Regist(
					rs.getString("mail"),
					rs.getInt("groupId")
				)
		);
	}
}
