package cn.edu.swpu.cins.openday.exception;

import cn.edu.swpu.cins.openday.enums.ExceptionMsgEnum;

public class OpenDayDataException extends OpenDayException {
	public OpenDayDataException() {
		super();
	}

	public OpenDayDataException(ExceptionMsgEnum msgEnum) {
		super(msgEnum);
	}

	public OpenDayDataException(String message) {
		super(message);
	}

	public OpenDayDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public OpenDayDataException(Throwable cause) {
		super(cause);
	}

	protected OpenDayDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
