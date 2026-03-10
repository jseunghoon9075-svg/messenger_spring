package com.app.chat.exception;

import lombok.Getter;

@Getter
public class ExternalApiException extends RuntimeException{
	
	private final String code;

	public ExternalApiException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ExternalApiException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

}
