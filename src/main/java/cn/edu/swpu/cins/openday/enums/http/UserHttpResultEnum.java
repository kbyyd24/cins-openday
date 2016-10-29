package cn.edu.swpu.cins.openday.enums.http;

public enum UserHttpResultEnum {
	ADD_USER_SUCCESS(200, "add user success and mail have sent to your mail"),
	ADD_USER_FAILED(201, "add user failed, please try again later"),
	EXISTED_USERNAME_AND_MAIL(202, "username and mail had been used, please input another"),
	EXISTED_USERNAME(203, "username had been used, please input another"),
	EXISTED_MALI(204, "mail had been used, please input another"),
	UNKNOWN_ERROR(205, "add user failed with unknown error"),
	PASSWORD_NOT_SAME(206, "please ensure your password are in the same"),
	ENABLE_TOKEN_SUCCESS(210, "enable user success"),
	ENABLE_TOKEN_INVALID(211, "error token"),
	ENABLE_TOKEN_TIMEOUT(212, "token timeout"),
	ENABLE_FAILED(213, "enable failed"),
	LOGIN_SUCCESS(220, "login success"),
	LOGIN_FAILED(221, "login failed"),
	SIGNOUT_SUCCESS(230, "sign out success"),
	SIGNOUT_FAILED(231, "sign out failed");

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
