package com.app.chat.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.chat.domain.dto.ErrorResponseDTO;
import com.app.chat.exception.GlobalException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ErrorResponseDTO> handleGlobal(GlobalException e, HttpServletRequest req){
		var status = e.getStatus().value();
		var body = ErrorResponseDTO.of(status, e.getCode(), e.getMessage(), req.getRequestURI());
		return ResponseEntity.status(e.getStatus()).body(body);
	}
	
//	Validation 어노테이션 쓸 경우 대비
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException e, HttpServletRequest req){
		var message = e.getBindingResult().getFieldErrors().stream()
					.findFirst()
					.map(err -> err.getField() + ": " + err.getDefaultMessage())
					.orElse("요청 값이 올바르지 않습니다.");
		
		var body = ErrorResponseDTO.of(400, "VALIDATION_ERROR", message, req.getRequestURI());
		return ResponseEntity.badRequest().body(body);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleUnknown(Exception e, HttpServletRequest req){
//		서버 로그는 상세, 응답 간결
		log.error("Unhandled exception. path={}", req.getRequestURI(), e);
		
		var body = ErrorResponseDTO.of(500, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.", req.getRequestURI());
		return ResponseEntity.status(500).body(body);
	}
}
