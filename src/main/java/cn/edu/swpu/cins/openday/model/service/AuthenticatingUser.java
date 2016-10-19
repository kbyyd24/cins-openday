package cn.edu.swpu.cins.openday.model.service;

public class AuthenticatingUser {

	private String mail;
	private String token;

	public AuthenticatingUser() {}

	public AuthenticatingUser(String mail, String token) {
		this.mail = mail;
		this.token = token;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AuthenticatingUser that = (AuthenticatingUser) o;

		if (getMail() != null ? !getMail().equals(that.getMail()) : that.getMail() != null) return false;
		return getToken() != null ? getToken().equals(that.getToken()) : that.getToken() == null;

	}

	@Override
	public int hashCode() {
		int result = getMail() != null ? getMail().hashCode() : 0;
		result = 31 * result + (getToken() != null ? getToken().hashCode() : 0);
		return result;
	}
}
