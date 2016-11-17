package cn.edu.swpu.cins.openday.exception;

import cn.edu.swpu.cins.openday.enums.ExceptionMsgEnum;

public class OpenDayException extends RuntimeException {
	public OpenDayException() {
		super();
	}

	public OpenDayException(ExceptionMsgEnum msgEnum) {
		this(msgEnum.name());
	}

	public OpenDayException(String message) {
		super(message);
	}

	public OpenDayException(String message, Throwable cause) {
		super(message, cause);
	}

	public OpenDayException(Throwable cause) {
		super(cause);
	}

	protected OpenDayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
