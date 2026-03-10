package com.app.chat.exception;

import org.springframework.http.HttpStatus;

public class GlobalException extends RuntimeException{
	
	private final HttpStatus status;
	private final String code;
	
	public GlobalException(HttpStatus status, String code, String message) {
		super(message);
		this.status = status;
		this.code = code;
	}

	public GlobalException(String message) {
		this(HttpStatus.BAD_REQUEST, "GLOBAL_EXCEPTION", message);
	}

	public GlobalException(HttpStatus status, String message) {
		this(status, "GLOBAL_EXCEPTION", message);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}
	
	

}
