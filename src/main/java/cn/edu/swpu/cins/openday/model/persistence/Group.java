package cn.edu.swpu.cins.openday.model.persistence;

public class Group {

	private Integer id;
	private String groupName;
	private Integer matchId;

	public Group() {}

	public Group(Integer id, String groupName, Integer matchId) {
		this.id = id;
		this.groupName = groupName;
		this.matchId = matchId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}
}
