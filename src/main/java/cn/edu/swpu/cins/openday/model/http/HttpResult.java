package cn.edu.swpu.cins.openday.model.http;

import cn.edu.swpu.cins.openday.enums.HttpResultEnum;

public class HttpResult {
	private int code;
	private String description;

	public HttpResult() {}

	public HttpResult(HttpResultEnum resultEnum) {
		this.code = resultEnum.getCode();
		this.description = resultEnum.getDescription();
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

	@Override
	public String toString() {
		return "HttpResult{" +
			"code=" + code +
			", description='" + description + '\'' +
			'}';
	}
}
