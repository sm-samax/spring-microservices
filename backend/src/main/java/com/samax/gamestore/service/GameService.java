package com.samax.gamestore.service;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.samax.gamestore.exception.NoSuchGameException;
import com.samax.gamestore.model.Game;
import com.samax.gamestore.repository.GameRepository;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private GridFsTemplate gridFsTemplate;
	
	@Autowired
	private GridFsOperations operations;
	
	@Autowired
	private ThreadPoolTaskExecutor executor;
	
	@Async
	public Future<Game> uploadGame(Game game, MultipartFile file) throws ExecutionException{
		return executor.submit(() -> {
			ObjectId id = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename());
			
			game.setDataId(id);
			game.setSize(file.getSize());
			
			return gameRepository.save(game);
		});
	}
	
	public Game peekGame(String id) {
		return gameRepository.findById(id).orElseThrow(NoSuchGameException::new);
	}
	
	@Async
	public Future<InputStream> downloadGame(String id) {
		return executor.submit(() -> {
			Game game = gameRepository.findById(id).get();
			GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(game.getDataId())));
			return operations.getResource(file).getInputStream();
		});
		
	}
}
