package cn.edu.swpu.cins.openday.enums;

public enum HttpResultEnum {
//	注册结果
	SIGN_UP_USER_SUCCESS(2000),
	SIGN_UP_USER_FAILED(2001),
	EXISTED_USERNAME_AND_MAIL(2002),
	EXISTED_USERNAME(2003),
	EXISTED_MALI(2004),
	PASSWORD_NOT_SAME(2005),
	UNKNOWN_ERROR(2006),
//	激活结果
	ENABLE_TOKEN_SUCCESS(2100),
	ENABLE_TOKEN_INVALID(2101),
	ENABLE_TOKEN_TIMEOUT(2102),
	ENABLE_FAILED(2103),
//	注销结果
	SIGN_OUT_SUCCESS(2300),
	SIGN_OUT_FAILED(2301),
//	报名结果
	JOIN_SUCCESS(4010),
	JOIN_FAILED(4011),
//	提交答案结果
	SAVE_ANSWER_SUCCESS(4020),
	SAVE_ANSWER_FAILED(4021),
	REQUEST_DENY(4022);

	private int code;

	HttpResultEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
