package cn.edu.swpu.cins.openday.model.http;

import cn.edu.swpu.cins.openday.enums.HttpResultEnum;

public class ActivityHttpResult {
	private int code;
	private String description;

	public ActivityHttpResult(HttpResultEnum result) {
		this.code = result.getCode();
		this.description = result.getDescription();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
