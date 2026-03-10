package com.app.chat.domain.dto;

import java.time.Instant;

import lombok.Getter;

@Getter
public class ErrorResponseDTO {
	Instant timestamp;
	int status;
	String code;
	String message;
	String path;

	public ErrorResponseDTO(Instant timestamp, int status, String code, String message, String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.code = code;
		this.message = message;
		this.path = path;
	}

	public static ErrorResponseDTO of(int status, String code, String message, String path) {
		return new ErrorResponseDTO(Instant.now(), status, code, message, path);
	}
}
