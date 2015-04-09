package com.despegar.moku.web.exception;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private String validationMessage;

	public ValidationException(String validationMessage) {
		super();
		this.validationMessage = validationMessage;
	}

	public String getValidationMessage() {
		return validationMessage;
	}

}
