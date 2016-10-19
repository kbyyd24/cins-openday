package cn.edu.swpu.cins.openday.dao.persistence;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class UserDao {
	public UserServiceResultEnum isValidUser(SignUpUser signUpUser) {
		return null;
	}

	public int signUpUser(SignUpUser signUpUser) {
		return 0;
	}
}
