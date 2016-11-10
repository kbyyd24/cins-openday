package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.exception.AddUserException;
import cn.edu.swpu.cins.openday.exception.NoUserToEnableException;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.*;

@Repository
public class UserDao {

	private static final String SELECT_BY_USERNAME_OR_MAIL =
		"select id, username, mail from user " +
			"where username = :username or mail = :mail";
	private static final String CREATE_NEW_USER =
		"INSERT INTO user(username, password, mail) VALUE " +
			"(:username, :password, :mail)";
	private static final String ENABLE_USER_BY_MAIL =
		"UPDATE user SET enable = TRUE WHERE mail = :mail";
	private static final String SELECT_BY_MAIL =
		"SELECT id, username, mail, password, enable FROM user " +
			"WHERE mail = :mail AND enable = TRUE ";
	private static final String SELECT_ID =
		"SELECT id, mail FROM user " +
			"WHERE mail = :mail1 OR mail = :mail2";

	private NamedParameterJdbcOperations jdbcOperations;

	@Autowired
	public UserDao(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public UserServiceResultEnum checkNewUser(SignUpUser signUpUser) {
		HashMap<String, String> queryMap = new HashMap<>(4);
		queryMap.put("username", signUpUser.getUsername());
		queryMap.put("mail", signUpUser.getMail());
		List<User> ret = jdbcOperations.query(
			SELECT_BY_USERNAME_OR_MAIL,
			queryMap,
			(rs, rowNum) -> new User(
				rs.getInt("id"),
				rs.getString("username"),
				rs.getString("mail")
			));
		if (ret.size() == 0) {
			return ADD_USER_USABLE;
		} else if (ret.size() == 2) {
			return EXISTED_USERNAME_AND_MAIL;
		}
		User existedUser = ret.get(0);
		if (existedUser.getUsername().equals(signUpUser.getUsername())) {
			return existedUser.getMail().equals(signUpUser.getMail()) ?
							EXISTED_USERNAME_AND_MAIL : EXISTED_USERNAME;
		}
		return EXISTED_MAIL;
	}

	public int signUpUser(SignUpUser signUpUser) {
		HashMap<String, String> insertMap = new HashMap<>(4);
		insertMap.put("username", signUpUser.getUsername());
		insertMap.put("password", signUpUser.getPassword());
		insertMap.put("mail", signUpUser.getMail());
		try {
			return jdbcOperations.update(CREATE_NEW_USER, insertMap);
		} catch (DataAccessException dae) {
			throw new AddUserException("exception happened when add user");
		}
	}


	public int enable(String mail) {
		HashMap<String, String> queryMap = new HashMap<>(2);
		queryMap.put("mail", mail);
		try {
			return jdbcOperations.update(ENABLE_USER_BY_MAIL, queryMap);
		} catch (DataAccessException dae) {
			throw new NoUserToEnableException("exception happened in UserDao when enable user: " + mail);
		}
	}

	public User signInUser(SignInUser signInUser) {
		HashMap<String, String> queryMap = new HashMap<>(2);
		queryMap.put("mail", signInUser.getMail());
		return jdbcOperations.query(SELECT_BY_MAIL, queryMap, rs -> {
			rs.next();
			return new User(
				rs.getInt("id"),
				rs.getString("username"),
				rs.getString("mail"),
				rs.getString("password"),
				rs.getBoolean("enable")
			);
		});
	}

	public List<User> getIds(String mail1, String mail2) {
		HashMap<String, String> queryMap = new HashMap<>();
		queryMap.put("mail1", mail1);
		queryMap.put("mail2", mail2);
		return jdbcOperations.query(
			SELECT_ID,
			queryMap,
			(rs, rowNum) -> new User(
				rs.getInt("id"),
				rs.getString("mail")
			));
	}
}
