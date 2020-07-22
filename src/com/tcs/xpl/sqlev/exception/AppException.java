package com.tcs.xpl.sqlev.exception;

public class AppException extends RuntimeException{

	private String message;

	public AppException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
