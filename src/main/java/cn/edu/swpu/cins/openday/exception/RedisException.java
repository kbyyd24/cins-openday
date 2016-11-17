package cn.edu.swpu.cins.openday.exception;

import cn.edu.swpu.cins.openday.enums.ExceptionMsgEnum;

public class RedisException extends OpenDayException {
	public RedisException() {
		super();
	}

	public RedisException(ExceptionMsgEnum msgEnum) {
		super(msgEnum);
	}

	public RedisException(String message) {
		super(message);
	}

	public RedisException(String message, Throwable cause) {
		super(message, cause);
	}

	public RedisException(Throwable cause) {
		super(cause);
	}

	protected RedisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
