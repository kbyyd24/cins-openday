package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.*;

@Repository
public class UserDao {

	private static final String SELECT_BY_USERNAME_OR_MAIL =
					"select id, username, mail from user " +
									"where username = :username or mail = :mail";
	private static final String CREATE_NEW_USER =
					"insert into user(username, password, mail) value " +
									"(:username, :password, :mail)";

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
		return existedUser.getUsername().equals(signUpUser.getUsername()) ?
						EXISTED_USERNAME : EXISTED_MAIL;
	}

	public int signUpUser(SignUpUser signUpUser) {
		HashMap<String, String> insertMap = new HashMap<>(4);
		insertMap.put("username", signUpUser.getUsername());
		insertMap.put("password", signUpUser.getPassword());
		insertMap.put("mail", signUpUser.getMail());
		return jdbcOperations.update(CREATE_NEW_USER, insertMap);
	}
}
