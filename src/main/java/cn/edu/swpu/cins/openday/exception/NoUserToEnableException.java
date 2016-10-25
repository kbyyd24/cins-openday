package cn.edu.swpu.cins.openday.exception;

public class NoUserToEnableException extends OpenDayException {
	public NoUserToEnableException() {
		super();
	}

	public NoUserToEnableException(String message) {
		super(message);
	}

	public NoUserToEnableException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoUserToEnableException(Throwable cause) {
		super(cause);
	}

	protected NoUserToEnableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
