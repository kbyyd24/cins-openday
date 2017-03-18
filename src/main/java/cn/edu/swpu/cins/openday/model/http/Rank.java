package cn.edu.swpu.cins.openday.model.http;

import cn.edu.swpu.cins.openday.model.service.ScoreRank;

public class Rank {
	private Integer rank;
	private String name;
	private float score;
	private Long time;

	public Rank() {}

	public Rank(ScoreRank scoreRank) {
		this.name = scoreRank.getName();
		this.score = scoreRank.getScore();
		this.time = scoreRank.getTime();
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Rank{" +
			"rank=" + rank +
			", name='" + name + '\'' +
			", score=" + score +
			", time=" + time +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Rank rank1 = (Rank) o;

		if (Float.compare(rank1.score, score) != 0) return false;
		if (rank != null ? !rank.equals(rank1.rank) : rank1.rank != null) return false;
		if (name != null ? !name.equals(rank1.name) : rank1.name != null) return false;
		return time != null ? time.equals(rank1.time) : rank1.time == null;
	}

	@Override
	public int hashCode() {
		int result = rank != null ? rank.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (score != +0.0f ? Float.floatToIntBits(score) : 0);
		result = 31 * result + (time != null ? time.hashCode() : 0);
		return result;
	}
}
