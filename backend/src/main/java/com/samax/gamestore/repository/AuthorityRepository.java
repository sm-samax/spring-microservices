package com.samax.gamestore.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.samax.gamestore.model.PersistedAuthority;

@Repository
public interface AuthorityRepository extends MongoRepository<PersistedAuthority, String> {

	public static final String USER_AUTHORITY = "USER";
	
	PersistedAuthority findByAuthority(String authority);
	
	default public PersistedAuthority findUserAuthority() {
		PersistedAuthority userAuthority = this.findByAuthority(USER_AUTHORITY);
		return userAuthority != null ? userAuthority : this.save(new PersistedAuthority(null, USER_AUTHORITY, List.of()));
	}
}
