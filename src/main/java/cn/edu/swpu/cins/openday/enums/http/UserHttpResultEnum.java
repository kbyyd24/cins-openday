package cn.edu.swpu.cins.openday.enums.http;

public enum UserHttpResultEnum {
	ADD_USER_SUCCESS(200, "add user success and mail have sent to your mail");

	private int code;
	private String description;

	UserHttpResultEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
