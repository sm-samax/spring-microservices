package com.samax.gamestore.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import com.samax.gamestore.exception.NoSuchGameException;
import com.samax.gamestore.model.Game;
import com.samax.gamestore.model.Payment;
import com.samax.gamestore.repository.GameRepository;
import com.samax.gamestore.repository.PaymentRepository;
import com.samax.gamestore.repository.UserRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Payment purchase(String id) {
		Game game = gameRepository.findById(id).orElseThrow(NoSuchGameException::new);
		
		Payment payment = new Payment();
		payment.setUser(getCurrentUser());
		payment.setItem(id);
		payment.setPrice(game.getPrice());
		payment.setTime(Instant.now());
		
		return paymentRepository.save(payment);
	}
	
	public Payment getPayment(String item) {
		return paymentRepository.findByUserAndItem(getCurrentUser(), item).orElse(null);
	}
	
	private String getCurrentUser() {
		String email = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject();
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new JwtException("The token was expired or malformed!"))
				.getId();
	}
}
