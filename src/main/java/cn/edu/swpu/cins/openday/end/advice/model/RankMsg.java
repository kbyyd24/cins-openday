package cn.edu.swpu.cins.openday.end.advice.model;

import java.util.List;

public class RankMsg {

	private List<String> mails;

	private int rank;

	public RankMsg(List<String> mails, int rank) {
		this.mails = mails;
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}

	public List<String> getMails() {
		return mails;
	}
}
