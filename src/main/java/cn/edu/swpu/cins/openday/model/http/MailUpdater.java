package cn.edu.swpu.cins.openday.model.http;

public class MailUpdater {
	private String preMail;
	private String newMail;
	private String password;

	public MailUpdater() {}

	public String getPreMail() {
		return preMail;
	}

	public void setPreMail(String preMail) {
		this.preMail = preMail;
	}

	public String getNewMail() {
		return newMail;
	}

	public void setNewMail(String newMail) {
		this.newMail = newMail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
