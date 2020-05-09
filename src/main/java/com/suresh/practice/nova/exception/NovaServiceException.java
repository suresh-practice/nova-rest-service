package com.suresh.practice.nova.exception;

public class NovaServiceException extends Exception {
	private static final long serialVersionUID = 1L;

	public NovaServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NovaServiceException(String message) {
		super(message);
	}

	public NovaServiceException(Throwable cause) {
		super(cause);
	}
}
