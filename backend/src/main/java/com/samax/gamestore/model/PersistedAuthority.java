package com.samax.gamestore.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection = "authority")
@Data
@AllArgsConstructor
public class PersistedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String authority;
	
	@JsonBackReference
	private List<User> users;
	
	@Override
	public String getAuthority() {
		return authority;
	}

}
