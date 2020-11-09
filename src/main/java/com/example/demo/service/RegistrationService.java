package com.example.demo.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserAuthenticationDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class RegistrationService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private final UserRepository userRepository;
	
	private final ModelMapper modelMapper;
	
	@Autowired
	public RegistrationService(UserRepository userRepository,ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	
	public Optional<User> create(UserAuthenticationDTO userDTO){
		User user = this.modelMapper.map(userDTO, User.class);
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		return Optional.ofNullable(this.userRepository.save(user));
	}
}
