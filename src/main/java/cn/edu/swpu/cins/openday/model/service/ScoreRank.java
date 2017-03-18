package cn.edu.swpu.cins.openday.model.service;

public class ScoreRank {
	private String name;
	private Integer id;
	private Float score;
	private Long time;

	public ScoreRank() {}

	public ScoreRank(String groupName, int groupId, float score, long time) {
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

	public Integer getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ScoreRank scoreRank = (ScoreRank) o;

		if (name != null ? !name.equals(scoreRank.name) : scoreRank.name != null) return false;
		if (id != null ? !id.equals(scoreRank.id) : scoreRank.id != null) return false;
		if (score != null ? !score.equals(scoreRank.score) : scoreRank.score != null) return false;
		return time != null ? time.equals(scoreRank.time) : scoreRank.time == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (id != null ? id.hashCode() : 0);
		result = 31 * result + (score != null ? score.hashCode() : 0);
		result = 31 * result + (time != null ? time.hashCode() : 0);
		return result;
	}
}
