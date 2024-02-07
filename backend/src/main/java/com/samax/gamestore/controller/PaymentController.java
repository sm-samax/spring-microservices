package com.samax.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samax.gamestore.model.Payment;
import com.samax.gamestore.service.PaymentService;

@RestController
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/purchase/{id}")
	public ResponseEntity<Payment> purchase(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.purchase(id));
	}
}
