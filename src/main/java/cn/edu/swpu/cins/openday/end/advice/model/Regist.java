package cn.edu.swpu.cins.openday.end.advice.model;

public class Regist {

	private String mail;

	private int groupId;

	public Regist(String mail, int groupId) {
		this.mail = mail;
		this.groupId = groupId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
}
