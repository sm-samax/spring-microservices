package com.samax.gamestore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationRequest {
	@NotBlank
	private String email;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
