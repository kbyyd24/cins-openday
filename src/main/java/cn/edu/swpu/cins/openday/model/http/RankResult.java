package cn.edu.swpu.cins.openday.model.http;

import java.util.List;

public class RankResult {

	private Integer myRank;

	private Integer myScore;

	private List<Rank> rankList;

	public RankResult() {}

	public Integer getMyRank() {
		return myRank;
	}

	public void setMyRank(Integer myRank) {
		this.myRank = myRank;
	}

	public Integer getMyScore() {
		return myScore;
	}

	public void setMyScore(Integer myScore) {
		this.myScore = myScore;
	}

	public List<Rank> getRankList() {
		return rankList;
	}

	public void setRankList(List<Rank> rankList) {
		this.rankList = rankList;
	}

	@Override
	public String toString() {
		return "RankResult{" +
			"myRank=" + myRank +
			", myScore=" + myScore +
			", rankList=" + rankList +
			'}';
	}
}
