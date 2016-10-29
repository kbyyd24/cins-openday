package cn.edu.swpu.cins.openday.model.http;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.persistence.User;

public class UserSignInResult {
	private Integer id;
	private String username;
	private String mail;
	private UserServiceResultEnum status;

	public UserSignInResult(User user, UserServiceResultEnum status) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.mail = user.getMail();
		this.status = status;
	}

	public UserSignInResult(UserServiceResultEnum status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getMail() {
		return mail;
	}

	public UserServiceResultEnum getStatus() {
		return status;
	}

	public void setStatus(UserServiceResultEnum status) {
		this.status = status;
	}
}
