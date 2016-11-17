package cn.edu.swpu.cins.openday.exception;

import cn.edu.swpu.cins.openday.enums.ExceptionMsgEnum;

public class AddUserException extends OpenDayException {
	public AddUserException() {
		super();
	}

	public AddUserException(ExceptionMsgEnum msgEnum) {
		super(msgEnum);
	}

	public AddUserException(String message) {
		super(message);
	}

	public AddUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public AddUserException(Throwable cause) {
		super(cause);
	}

	protected AddUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
