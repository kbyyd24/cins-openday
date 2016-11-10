package cn.edu.swpu.cins.openday.model.persistence;

import java.util.List;

public class Registration {
	private Integer id;
	private Integer matchId;
	private Integer userId1;
	private Integer userId2;
	private Integer groupId;

	public Registration() {}

	public Registration(Integer matchId, List<User> users, Integer groupId) {
		this.matchId = matchId;
		this.userId1 = users.get(0).getId();
		this.userId2 = users.get(1).getId();
		this.groupId = groupId;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Integer getUserId1() {
		return userId1;
	}

	public void setUserId1(Integer userId1) {
		this.userId1 = userId1;
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

	public Integer getUserId2() {
		return userId2;
	}

	public void setUserId2(Integer userId2) {
		this.userId2 = userId2;
	}
}
