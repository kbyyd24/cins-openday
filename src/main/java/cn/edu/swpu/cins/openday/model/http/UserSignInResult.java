package cn.edu.swpu.cins.openday.model.http;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.persistence.User;

public class UserSignInResult {
	private String UUID;
	private Integer id;
	private String username;
	private String mail;
	private UserServiceResultEnum status;

	public UserSignInResult(User user, UserServiceResultEnum status) {
		this(user);
		this.status = status;
	}

	public UserSignInResult(UserServiceResultEnum status) {
		this.status = status;
	}

	public UserSignInResult(String token, User user) {
		this(user);
		this.UUID = token;
	}

	public UserSignInResult(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.mail = user.getMail();
	}

	public UserSignInResult(String token, int id, String username, String mail) {
		this.UUID = token;
		this.id = id;
		this.username = username;
		this.mail = mail;
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

	public String getUUID() {
		return UUID;
	}
}
