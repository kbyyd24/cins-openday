package cn.edu.swpu.cins.openday.exception;

public class AddUserException extends OpenDayDataException {
	public AddUserException() {
		super();
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
