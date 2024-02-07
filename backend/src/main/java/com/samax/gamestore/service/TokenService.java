package com.samax.gamestore.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.samax.gamestore.model.PersistedAuthority;
import com.samax.gamestore.model.User;

@Service
public class TokenService {

	@Value("${spring.application.name:http://localhost:8080}")
	private String issuer; 
	
	@Autowired
	private JwtEncoder jwtEncoder;
	
	@Autowired
	private JwtDecoder jwtDecoder;

	public Jwt generateToken(User user) {
		Instant now = Instant.now();
		Instant expiresAt = now.plus(10, ChronoUnit.MINUTES);
		
		String scope = user.getAuthorities().stream().map(PersistedAuthority::getAuthority)
				.collect(Collectors.joining(" "));

		JwtClaimsSet claims = JwtClaimsSet.builder().issuedAt(now)
				.expiresAt(expiresAt)
				.issuer(issuer)
				.subject(user.getEmail())
				.claim("scope", scope)
				.build();

		return jwtEncoder.encode(JwtEncoderParameters.from(claims));
	}
	
	public Jwt validateToken(String token) {
		Jwt jwt = jwtDecoder.decode(token);
		return jwt;
	}
	
}
