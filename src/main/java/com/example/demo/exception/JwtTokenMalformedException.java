package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.security.core.AuthenticationException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JwtTokenMalformedException extends AuthenticationException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 178440041469999289L;

	public JwtTokenMalformedException(String msg) {
        super(msg);
    }
}

