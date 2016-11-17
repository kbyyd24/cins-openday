package cn.edu.swpu.cins.openday.exception;

import cn.edu.swpu.cins.openday.enums.ExceptionMsgEnum;

public class FileException extends OpenDayException {
	public FileException() {
		super();
	}

	public FileException(ExceptionMsgEnum msgEnum) {
		super(msgEnum);
	}

	public FileException(String message) {
		super(message);
	}

	public FileException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileException(Throwable cause) {
		super(cause);
	}

	protected FileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
