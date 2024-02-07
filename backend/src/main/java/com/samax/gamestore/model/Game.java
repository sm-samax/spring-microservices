package com.samax.gamestore.model;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "game")
@Data
public class Game {
	@Id
	private String id;
	private String name;
	private String description;
	private Long size;
	private ObjectId dataId;
	private BigDecimal price;
}
