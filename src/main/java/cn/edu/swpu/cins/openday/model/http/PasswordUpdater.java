package cn.edu.swpu.cins.openday.model.http;

public class PasswordUpdater {
	private String mail;
	private String prePassword;
	private String newPassword;
	private String reNewPassword;

	public PasswordUpdater() {}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPrePassword() {
		return prePassword;
	}

	public void setPrePassword(String prePassword) {
		this.prePassword = prePassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getReNewPassword() {
		return reNewPassword;
	}

	public void setReNewPassword(String reNewPassword) {
		this.reNewPassword = reNewPassword;
	}
}
