package io.medhanie.erient.exception;

public enum ErrorList {

	UNABLE_TO_CONNECT(ErrorType.INVALID_DATA, "Connection has error.", "ERR01", ErrorLevel.ERROR);

	private ErrorType type;
	private String errorMessage;
	private String code;
	private ErrorLevel level;

	private ErrorList(ErrorType type, String errorMessage, String code, ErrorLevel level) {
		this.type = type;
		this.errorMessage = errorMessage;
		this.code = code;
		this.level = level;
	}

	public ErrorType getType() {
		return type;
	}

	public void setType(ErrorType type) {
		this.type = type;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ErrorLevel getLevel() {
		return level;
	}

	public void setLevel(ErrorLevel level) {
		this.level = level;
	}

}
