package cn.edu.swpu.cins.openday.model.http;

public class TeamMsgGetter {
	private Integer matchId;
	private Integer userId;

	public TeamMsgGetter() {}

	public TeamMsgGetter(int userId, int matchId) {
		this.userId = userId;
		this.matchId = matchId;
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
}
