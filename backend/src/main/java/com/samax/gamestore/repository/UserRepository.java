package com.samax.gamestore.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.samax.gamestore.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Optional<User> findByEmailAndPassword(String email, String password);
}
