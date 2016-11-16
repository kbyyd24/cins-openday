package cn.edu.swpu.cins.openday.model.http;

public class SignUpUser {
	private String username;
	private String password;
	private String repassword;
	private String mail;

	public SignUpUser() {}

	public SignUpUser(String username, String password, String repassword, String mail) {
		this.username = username;
		this.password = password;
		this.repassword = repassword;
		this.mail = mail;
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

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public boolean isPasswordValid() {
		return password.equals(repassword);
	}

	@Override
	public String toString() {
		return "SignUpUser{" +
			"username='" + username + '\'' +
			", password='" + password + '\'' +
			", repassword='" + repassword + '\'' +
			", mail='" + mail + '\'' +
			'}';
	}
}
