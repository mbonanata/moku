package com.despegar.moku.service.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException(String errorMessage) {
		super(errorMessage);
	}

	public ServiceException(String errorMessage, Throwable t) {
		super(errorMessage, t);
	}
}
