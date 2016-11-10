package cn.edu.swpu.cins.openday.exception;

public class GroupException extends OpenDayException {
	public GroupException() {
		super();
	}

	public GroupException(String message) {
		super(message);
	}

	public GroupException(String message, Throwable cause) {
		super(message, cause);
	}

	public GroupException(Throwable cause) {
		super(cause);
	}

	protected GroupException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
