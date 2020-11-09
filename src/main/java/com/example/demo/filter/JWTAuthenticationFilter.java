package com.example.demo.filter;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.dto.UserAuthenticationDTO;
import com.example.demo.error.ErrorDetails;
import com.example.demo.security.JWTConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			
			UserAuthenticationDTO user = new ObjectMapper().readValue(request.getInputStream(),UserAuthenticationDTO.class);
			return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
		}catch(IOException exception) {
			throw new RuntimeException(exception.getMessage());
		}
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		// custom error response class used across my project
		ErrorDetails errorResponse = ErrorDetails.builder()
					          .timestamp(OffsetDateTime.now())
					          .status(HttpStatus.FORBIDDEN.value())
					          .title("Username or Password Invalid !!!!")
					          .detail(failed.getMessage())
					          .developerMessage(failed.getClass().getName())
					          .build();
				
		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));	
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String email =  ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();
		
		//create Token
		String token = Jwts
				.builder()
				 .setSubject(email)
	             .setExpiration(new Date(System.currentTimeMillis() + JWTConstants.EXPIRATION_TIME))
	             .signWith(SignatureAlgorithm.HS512, JWTConstants.SECRET)
	             .compact();
		
		// Add token in response
		String bearerToken = JWTConstants.TOKEN_PREFIX + token;
        response.getWriter().write(bearerToken);
        response.addHeader(JWTConstants.HEADER_STRING, bearerToken);
	}
	
	
}
