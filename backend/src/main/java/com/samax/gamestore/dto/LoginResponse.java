package com.samax.gamestore.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	private String username;
	private String accessToken;
	private Instant validUntil;
}
