package cn.edu.swpu.cins.openday.model.persistence;

public class Match {
	private Integer id;
	private String matchName;
	private String detail;
	private Long startTime;
	private Long endTime;

	public Match(int id, String match_name, String detail, long start_time, long end_time) {
		this.id = id;
		matchName = match_name;
		this.detail = detail;
		startTime = start_time;
		endTime = end_time;
	}
}
