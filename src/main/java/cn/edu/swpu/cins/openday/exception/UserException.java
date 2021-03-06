package cn.edu.swpu.cins.openday.exception;

import cn.edu.swpu.cins.openday.enums.ExceptionMsgEnum;

public class UserException extends OpenDayException {
	public UserException() {
		super();
	}

	public UserException(ExceptionMsgEnum msgEnum) {
		super(msgEnum);
	}

	public UserException(String message) {
		super(message);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserException(Throwable cause) {
		super(cause);
	}

	protected UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
