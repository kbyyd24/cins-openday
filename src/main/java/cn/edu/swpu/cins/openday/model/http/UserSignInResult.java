package cn.edu.swpu.cins.openday.model.http;

import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.persistence.User;

public class UserSignInResult {
	private String token;
	private Integer id;
	private String username;
	private String mail;
	private String status;

	public UserSignInResult(User user, UserServiceResultEnum status) {
		this(user);
		this.status = status.name();
	}

	public UserSignInResult(UserServiceResultEnum status) {
		this.status = status.name();
	}

	public UserSignInResult(String token, User user) {
		this(user);
		this.token = token;
	}

	public UserSignInResult(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.mail = user.getMail();
	}

	public UserSignInResult(String token, int id, String username, String mail) {
		this.token = token;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "UserSignInResult{" +
			"token='" + token + '\'' +
			", id=" + id +
			", username='" + username + '\'' +
			", mail='" + mail + '\'' +
			", status='" + status + '\'' +
			'}';
	}
}
