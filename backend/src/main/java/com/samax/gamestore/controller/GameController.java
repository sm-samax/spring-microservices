package com.samax.gamestore.controller;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.samax.gamestore.exception.GameNotPurchasedException;
import com.samax.gamestore.model.Game;
import com.samax.gamestore.service.GameService;
import com.samax.gamestore.service.PaymentService;

@RestController
public class GameController {

	@Autowired
	private GameService gameService;
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/upload")
	public ResponseEntity<Game> uploadGame(@RequestPart Game game, @RequestPart MultipartFile file)
			throws ExecutionException, InterruptedException {
		return ResponseEntity.status(HttpStatus.CREATED).body(gameService.uploadGame(game, file).get());
	}
	
	@GetMapping("/game/{id}")
	public ResponseEntity<Game> peekGame(@PathVariable String id) {
		return ResponseEntity.ok(gameService.peekGame(id));
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<StreamingResponseBody> downloadGame(@PathVariable String id) 
			throws ExecutionException, InterruptedException {
		
		if(paymentService.getPayment(id) == null) {
			throw new GameNotPurchasedException();
		}
		
		Game game = gameService.peekGame(id);
		InputStream in = gameService.downloadGame(id).get();
		StreamingResponseBody responseBody = out -> in.transferTo(out);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.exe", game.getName()))
				.header(HttpHeaders.CONTENT_LENGTH, game.getSize().toString())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(responseBody);
	}
}
