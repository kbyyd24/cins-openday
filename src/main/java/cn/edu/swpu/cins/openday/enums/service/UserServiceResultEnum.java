package cn.edu.swpu.cins.openday.enums.service;

public enum UserServiceResultEnum {
	ADD_USER_SUCCESS,
	PASSWORD_NOT_SAME,
	ADD_USER_FAILED,
	EXISTED_USERNAME_AND_MAIL,
	EXISTED_USERNAME,
	EXISTED_MAIL,
	ENABLE_TOKEN_SUCCESS,
	ENABLE_TOKEN_INVALID, ENABLE_TOKEN_TIMEOUT, ADD_USER_USABLE
}
