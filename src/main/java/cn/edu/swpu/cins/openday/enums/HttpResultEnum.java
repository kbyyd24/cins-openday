package cn.edu.swpu.cins.openday.enums;

public enum HttpResultEnum {
	REQUEST_SUCCESS(200, "ok"),
	BAD_REQUEST(400, "bad request"),
	UNAUTHORIZED(401, "unauthorized"),
	FORBIDDEN(403, "forbidden"),
	NOT_FOUND(404, "not found"),
	INTERNAL_SERVER_ERROR(500, "something wrong"),
	ADD_USER_SUCCESS(2000, "add user success and mail have sent to your mail"),
	ADD_USER_FAILED(2001, "add user failed, please try again later"),
	EXISTED_USERNAME_AND_MAIL(2002, "username and mail had been used, please input another"),
	EXISTED_USERNAME(2003, "username had been used, please input another"),
	EXISTED_MALI(2004, "mail had been used, please input another"),
	UNKNOWN_ERROR(2005, "add user failed with unknown error"),
	PASSWORD_NOT_SAME(2006, "please ensure your password are in the same"),
	ENABLE_TOKEN_SUCCESS(2100, "enable user success"),
	ENABLE_TOKEN_INVALID(2101, "error token"),
	ENABLE_TOKEN_TIMEOUT(2102, "token timeout"),
	ENABLE_FAILED(2103, "enable failed"),
	SIGNOUT_SUCCESS(2300, "sign out success"),
	SIGNOUT_FAILED(2301, "sign out failed"),
	SAVE_SUCCESS(3000, "save activity success"),
	SAVE_FAILED(3001, "save activity failed"),
	ADD_SUCCESS(4000, "add match success"),
	ADD_FAILED(4001, "add match failed");

	private int code;
	private String description;

	HttpResultEnum(int code, String description) {
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
