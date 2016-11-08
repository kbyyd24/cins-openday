package cn.edu.swpu.cins.openday.model.http;

import cn.edu.swpu.cins.openday.enums.HttpResultEnum;

public class MatchHttpResult {
	private String description;
	private int code;

	public MatchHttpResult() {}

	public MatchHttpResult(HttpResultEnum resultEnum) {
		this.code = resultEnum.getCode();
		this.description = resultEnum.getDescription();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
