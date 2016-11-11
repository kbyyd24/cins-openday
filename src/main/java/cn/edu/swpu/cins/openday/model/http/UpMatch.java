package cn.edu.swpu.cins.openday.model.http;

public class UpMatch {
	private String matchName;
	private String detail;
	private Long startTime;
	private Long endTime;
	private String judgeStandard;
	private String commitRegular;
	private String award;

	public String getJudgeStandard() {
		return judgeStandard;
	}

	public void setJudgeStandard(String judgeStandard) {
		this.judgeStandard = judgeStandard;
	}

	public String getCommitRegular() {
		return commitRegular;
	}

	public void setCommitRegular(String commitRegular) {
		this.commitRegular = commitRegular;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public UpMatch() {}

	public UpMatch(String matchName, String detail, Long startTime, Long endTime) {
		this.matchName = matchName;
		this.detail = detail;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getMatchName() {
		return matchName;
	}
}
