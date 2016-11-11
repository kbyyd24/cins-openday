package cn.edu.swpu.cins.openday.model.service;

public class ScoreRank {
	private String name;
	private Integer id;
	private Integer score;
	private Long time;

	public ScoreRank() {}

	public ScoreRank(String groupName, int groupId, int score, long time) {
		name = groupName;
		id = groupId;
		this.score = score;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getId() {
		return id;
	}
}
