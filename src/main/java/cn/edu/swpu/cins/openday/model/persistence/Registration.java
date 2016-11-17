package cn.edu.swpu.cins.openday.model.persistence;

public class Registration {
	private Integer id;
	private Integer matchId;
	private Integer userId;
	private Integer groupId;
	private boolean captain;

	public Registration() {}

	public Registration(int id) {
		this.id = id;
	}

	public Registration(int matchId, int userId, int groupId, boolean captain) {
		this.matchId = matchId;
		this.userId = userId;
		this.groupId = groupId;
		this.captain = captain;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCaptain(Boolean captain) {
		this.captain = captain;
	}

	public Boolean getCaptain() {
		return captain;
	}
}
