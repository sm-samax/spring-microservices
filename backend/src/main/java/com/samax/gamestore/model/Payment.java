package com.samax.gamestore.model;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "payment")
@Data
public class Payment {
	@Id
	private String id;
	private String user;
	private String item;
	private BigDecimal price;
	private Instant time;
}
