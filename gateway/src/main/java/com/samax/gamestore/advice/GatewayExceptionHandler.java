package com.samax.gamestore.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GatewayExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GatewayExceptionHandler.class);
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handleResponseStatus(ResponseStatusException ex) {
		switch (ex.getStatusCode().value()) {
		case 401:
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in!");
		case 403:
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied!");
		case 404:
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The url is not found!");
		case 503:
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Please try again later!");
		default:
			logger.error("{}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something wrong with the server!");
		}
		
		
	}
}
