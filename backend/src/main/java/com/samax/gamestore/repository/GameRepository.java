package com.samax.gamestore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.samax.gamestore.model.Game;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {

}
