package cn.edu.swpu.cins.openday.exception;

public class OpenDayException extends RuntimeException {
	public OpenDayException() {
		super();
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
