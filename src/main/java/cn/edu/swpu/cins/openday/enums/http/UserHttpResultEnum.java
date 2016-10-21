package cn.edu.swpu.cins.openday.enums.http;

public enum UserHttpResultEnum {
	ADD_USER_SUCCESS(200, "add user success and mail have sent to your mail"),
	ADD_USER_FAILED(201, "add user failed, please try again later"),
	EXISTED_USERNAME_AND_MAIL(202, "username and mail had been used, please input another"),
	EXISTED_USERNAME(203, "username had been used, please input another"),
	EXISTED_MALI(204, "mail had been used, please input another"),
	UNKNOWN_ERROR(205, "add user failed with unknown error"),
	PASSWORD_NOT_SAME(206, "please ensure your password are in the same");

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
