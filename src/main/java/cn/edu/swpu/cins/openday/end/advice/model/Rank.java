package cn.edu.swpu.cins.openday.end.advice.model;

public class Rank {

	private int groupId;

	private int rank;

	public Rank(int groupId) {
		this.groupId = groupId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
