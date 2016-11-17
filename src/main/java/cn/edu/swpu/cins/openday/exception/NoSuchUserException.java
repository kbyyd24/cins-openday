package cn.edu.swpu.cins.openday.exception;

import cn.edu.swpu.cins.openday.enums.ExceptionMsgEnum;

public class NoSuchUserException extends OpenDayException{
	public NoSuchUserException() {
		super();
	}

	public NoSuchUserException(ExceptionMsgEnum msgEnum) {
		super(msgEnum);
	}

	public NoSuchUserException(String message) {
		super(message);
	}

	public NoSuchUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchUserException(Throwable cause) {
		super(cause);
	}

	protected NoSuchUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
