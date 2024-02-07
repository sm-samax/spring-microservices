package com.samax.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.samax.gamestore.dto.LoginRequest;
import com.samax.gamestore.dto.LoginResponse;
import com.samax.gamestore.dto.RegistrationRequest;
import com.samax.gamestore.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login) {
		return ResponseEntity.ok(userService.loginUser(login));
	}
	
	@PostMapping("/register")
	public ResponseEntity<LoginResponse> registration(@RequestBody @Valid RegistrationRequest registration) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(registration));
	}
}
