package cn.edu.swpu.cins.openday.model.http;

public class TeamMsg {
	private String teamName;
	private String captain;
	private String captainMail;
	private String teamMember;
	private String teamMemberMail;

	public TeamMsg() {}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getCaptain() {
		return captain;
	}

	public void setCaptain(String captain) {
		this.captain = captain;
	}

	public String getCaptainMail() {
		return captainMail;
	}

	public void setCaptainMail(String captainMail) {
		this.captainMail = captainMail;
	}

	public String getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(String teamMember) {
		this.teamMember = teamMember;
	}

	public String getTeamMemberMail() {
		return teamMemberMail;
	}

	public void setTeamMemberMail(String teamMemberMail) {
		this.teamMemberMail = teamMemberMail;
	}
}
