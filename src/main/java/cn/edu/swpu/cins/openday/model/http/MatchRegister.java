package cn.edu.swpu.cins.openday.model.http;

public class MatchRegister {
	private String mail1;
	private String mail2;
	private Integer matchId;
	private String groupName;

	public String getMail1() {
		return mail1;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMail1(String mail1) {
		this.mail1 = mail1;
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

	public String getMail2() {
		return mail2;
	}

	public void setMail2(String mail2) {
		this.mail2 = mail2;
	}
}
