package com.samax.gamestore.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.samax.gamestore.exception.EmailAlreadyInUseException;
import com.samax.gamestore.exception.GameNotPurchasedException;
import com.samax.gamestore.exception.IncorrectLoginException;
import com.samax.gamestore.exception.NoSuchGameException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoSuchGameException.class)
	public ResponseEntity<String> handleNoSuchGame() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no such game!");
	}
	
	@ExceptionHandler(GameNotPurchasedException.class)
	public ResponseEntity<String> handleGameNotPurchased() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The game must be purchased to be downloaded!");
	}
	
	@ExceptionHandler(EmailAlreadyInUseException.class)
	public ResponseEntity<String> handleEmailAlreadyInUse() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This email is already being used by another account!");
	}
	
	@ExceptionHandler(IncorrectLoginException.class)
	public ResponseEntity<String> handleIncorrectLogin() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The email or the password were incorrect!");
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleIncorrectRequests() {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some fields were incorrectly filled!");
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<String> handleJwt(JwtException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
