package cn.edu.swpu.cins.openday.model.persistence;

public class User {
	private Integer id;
	private String username;
	private String password;
	private String mail;
	private Boolean enable;

	public User() {}

	public User(int id, String username, String mail) {
		this.id = id;
		this.username = username;
		this.mail = mail;
	}

	public User(int id, String username, String mail, String password, boolean enable) {
		this.id = id;
		this.username = username;
		this.mail = mail;
		this.password = password;
		this.enable = enable;
	}

	public User(int id, String mail) {
		this.id = id;
		this.mail = mail;
	}

	public User(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
}
