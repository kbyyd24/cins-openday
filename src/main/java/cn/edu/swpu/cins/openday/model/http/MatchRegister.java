package cn.edu.swpu.cins.openday.model.http;

public class MatchRegister {
	private String teammate;
	private String groupName;

	public MatchRegister() {}

	public MatchRegister(String teammate, String groupName) {
		this.teammate = teammate;
		this.groupName = groupName;
	}

	public String getTeammate() {
		return teammate;
	}

	public void setTeammate(String teammate) {
		this.teammate = teammate;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
