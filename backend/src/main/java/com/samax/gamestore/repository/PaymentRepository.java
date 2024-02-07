package com.samax.gamestore.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.samax.gamestore.model.Payment;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String>{
	Optional<Payment> findByUserAndItem(String user, String item); 
}
