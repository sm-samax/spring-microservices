package com.samax.gamestore.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Document(collection = "user")
@Data
public class User {
	@Id
	private String id;
	private String email;
	private String username;
	@JsonIgnore
	private String password;
	private List<PersistedAuthority> authorities;
}