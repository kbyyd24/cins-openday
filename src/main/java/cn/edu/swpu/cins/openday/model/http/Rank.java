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
}
