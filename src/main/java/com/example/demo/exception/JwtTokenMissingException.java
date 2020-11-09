package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JwtTokenMissingException extends AuthenticationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -212229509956213066L;

	public JwtTokenMissingException(String msg) {
        super(msg);
    }
}
