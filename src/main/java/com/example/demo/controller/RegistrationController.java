package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserAuthenticationDTO;
import com.example.demo.service.RegistrationService;

@RestController
@RequestMapping("/signup")
@CrossOrigin
public class RegistrationController {
	
	private final RegistrationService registrationService;
	
	@Autowired
	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody UserAuthenticationDTO userDTO){
        return new ResponseEntity<>(registrationService.create(userDTO).get(),HttpStatus.CREATED);
	}
}