package cn.edu.swpu.cins.openday.model.service;

public class TeammateMsg {
	private Integer id;
	private Boolean captain;

	public TeammateMsg() {}

	public TeammateMsg(int user_id, boolean captain) {
		id = user_id;
		this.captain = captain;
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getCaptain() {
		return captain;
	}

	public void setCaptain(Boolean captain) {
		this.captain = captain;
	}
}
