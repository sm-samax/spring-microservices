package com.samax.gamestore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samax.gamestore.dto.LoginRequest;
import com.samax.gamestore.dto.LoginResponse;
import com.samax.gamestore.dto.RegistrationRequest;
import com.samax.gamestore.exception.EmailAlreadyInUseException;
import com.samax.gamestore.exception.IncorrectLoginException;
import com.samax.gamestore.model.PersistedAuthority;
import com.samax.gamestore.model.User;
import com.samax.gamestore.repository.AuthorityRepository;
import com.samax.gamestore.repository.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenService tokenService;
	
	public LoginResponse registerUser(RegistrationRequest registration) {
		if(userRepository.findByEmail(registration.getEmail()).isPresent()) {
			throw new EmailAlreadyInUseException();
		}
		
		String encodedPassword = passwordEncoder.encode(registration.getPassword());
		List<PersistedAuthority> authorities = List.of(authorityRepository.findUserAuthority());
		
		User user = new User();
		user.setEmail(registration.getEmail());
		user.setUsername(registration.getUsername());
		user.setPassword(encodedPassword);
		user.setAuthorities(authorities);
		
		userRepository.save(user);
		
		Jwt jwt = tokenService.generateToken(user);
		return new LoginResponse(user.getUsername(), jwt.getTokenValue(), jwt.getExpiresAt());
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("No user with username: %s", username)));
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getEmail())
				.authorities(user.getAuthorities())
				.build();
	}

	public LoginResponse loginUser(LoginRequest login) {
		User user = userRepository.findByEmail(login.getEmail())
				.orElseThrow(IncorrectLoginException::new);
		if(!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
			throw new IncorrectLoginException();
		}
		
		Jwt jwt = tokenService.generateToken(user);
		return new LoginResponse(user.getUsername(), jwt.getTokenValue(), jwt.getExpiresAt());
	}
}
