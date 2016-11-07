package cn.edu.swpu.cins.openday.model.http;

public class UpMatch {
	private String matchName;
	private String detail;
	private Long startTime;
	private Long endTime;

	public UpMatch() {}

	public UpMatch(String matchName, String detail, Long startTime, Long endTime) {
		this.matchName = matchName;
		this.detail = detail;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
