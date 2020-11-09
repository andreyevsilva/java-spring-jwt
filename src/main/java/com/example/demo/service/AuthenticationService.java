package com.example.demo.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Autowired
	public AuthenticationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Optional<User> user = Optional.ofNullable(this.userRepository.findByEmailContainingIgnoreCase(username))
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
		 		 
		 
		 return new org.springframework.security.core.userdetails.User
	                (user.get().getEmail(), user.get().getPassword(), true, true, true, true, new ArrayList<>());
	}
}
