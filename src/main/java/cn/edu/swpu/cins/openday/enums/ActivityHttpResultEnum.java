package cn.edu.swpu.cins.openday.enums;

public enum ActivityHttpResultEnum {
	SAVE_SUCCESS(300, "save activity success"),
	SAVE_FAILED(301, "save activity failed");

	private int code;
	private String description;

	ActivityHttpResultEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
