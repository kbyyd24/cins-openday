package cn.edu.swpu.cins.openday.enums.http;

public enum MatchHttpResultEnum {
	ADD_SUCCESS(400, "add match success"), ADD_FAILED(401, "add match failed");
	private int code;
	private String description;

	MatchHttpResultEnum(int code, String description) {
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
