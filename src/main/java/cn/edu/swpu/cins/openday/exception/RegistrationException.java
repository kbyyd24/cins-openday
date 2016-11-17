package cn.edu.swpu.cins.openday.exception;

import cn.edu.swpu.cins.openday.enums.ExceptionMsgEnum;

public class RegistrationException extends OpenDayException{
	public RegistrationException() {
		super();
	}

	public RegistrationException(ExceptionMsgEnum msgEnum) {
		super(msgEnum);
	}

	public RegistrationException(String message) {
		super(message);
	}

	public RegistrationException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegistrationException(Throwable cause) {
		super(cause);
	}

	protected RegistrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
