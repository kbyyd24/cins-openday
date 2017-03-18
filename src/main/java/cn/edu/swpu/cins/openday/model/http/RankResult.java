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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RankResult that = (RankResult) o;

		if (myRank != null ? !myRank.equals(that.myRank) : that.myRank != null) return false;
		if (myScore != null ? !myScore.equals(that.myScore) : that.myScore != null) return false;
		return rankList != null ? rankList.equals(that.rankList) : that.rankList == null;
	}

	@Override
	public int hashCode() {
		int result = myRank != null ? myRank.hashCode() : 0;
		result = 31 * result + (myScore != null ? myScore.hashCode() : 0);
		result = 31 * result + (rankList != null ? rankList.hashCode() : 0);
		return result;
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
