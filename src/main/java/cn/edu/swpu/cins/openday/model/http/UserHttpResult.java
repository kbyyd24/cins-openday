package cn.edu.swpu.cins.openday.model.http;

import cn.edu.swpu.cins.openday.enums.HttpResultEnum;

public class UserHttpResult {
	private int code;
	private String description;

	public UserHttpResult(HttpResultEnum userResultEnum) {
		this.code = userResultEnum.getCode();
		this.description = userResultEnum.getDescription();
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
		return "UserHttpResult{" +
			"code=" + code +
			", description='" + description + '\'' +
			'}';
	}
}
