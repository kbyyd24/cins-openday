package cn.edu.swpu.cins.openday.model.http;

public class MatchRegister {
	private String mail;
	private Integer matchId;
	private String groupName;

	public String getMail() {
		return mail;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}