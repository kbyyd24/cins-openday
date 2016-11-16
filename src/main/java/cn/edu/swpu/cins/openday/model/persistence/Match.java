package cn.edu.swpu.cins.openday.model.persistence;

public class Match {
	private Integer id;
	private String matchName;
	private String detail;
	private String timePlan;
	private String dataLink;
	private String dataPassword;
	private String commitRegular;
	private String judgeStandard;
	private String award;

	public Match(int id, String match_name, String detail, String timePlan,
	             String award, String judge_standard, String commit_regular) {
		this.id = id;
		matchName = match_name;
		this.detail = detail;
		this.timePlan = timePlan;
		this.award = award;
		judgeStandard = judge_standard;
		commitRegular = commit_regular;
	}

	public String getCommitRegular() {
		return commitRegular;
	}

	public void setCommitRegular(String commitRegular) {
		this.commitRegular = commitRegular;
	}

	public String getJudgeStandard() {
		return judgeStandard;
	}

	public void setJudgeStandard(String judgeStandard) {
		this.judgeStandard = judgeStandard;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public Match(String data_link, String data_password) {
		dataLink = data_link;
		dataPassword = data_password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMatchName() {
		return matchName;
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

	public String getDataLink() {
		return dataLink;
	}

	public void setDataLink(String dataLink) {
		this.dataLink = dataLink;
	}

	public String getDataPassword() {
		return dataPassword;
	}

	public void setDataPassword(String dataPassword) {
		this.dataPassword = dataPassword;
	}

	public Match(int id, String match_name, String detail, long start_time, long end_time) {
		this.id = id;
		matchName = match_name;
		this.detail = detail;
	}

	public Match() {}

	public String getTimePlan() {
		return timePlan;
	}

	public void setTimePlan(String timePlan) {
		this.timePlan = timePlan;
	}
}
