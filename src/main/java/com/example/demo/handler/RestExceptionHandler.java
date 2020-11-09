package com.example.demo.handler;


import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.error.ErrorDetails;
import com.example.demo.exception.ResourceNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail(rfnException.getMessage())
                .developerMessage(rfnException.getClass().getName())
                .build();
        
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureJwtTokenException(SignatureException signException) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Invalid JWT signature")
                .detail(signException.getMessage())
                .developerMessage(signException.getClass().getName())
                .build();
        
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
	
	
	@ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<?> handleMalformedJwtException(MalformedJwtException malformedException) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Malformed JWT Token")
                .detail(malformedException.getMessage())
                .developerMessage(malformedException.getClass().getName())
                .build();
        
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtExceptionJWTTokenException(ExpiredJwtException expiredException) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Expired JWT Token")
                .detail(expiredException.getMessage())
                .developerMessage(expiredException.getClass().getName())
                .build();
        
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
	
	
	@ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<?> handleUnsupportedJwtExceptionJWTTokenException(UnsupportedJwtException unsupportedJwtException) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Unsupported JWT Token")
                .detail(unsupportedJwtException.getMessage())
                .developerMessage(unsupportedJwtException.getClass().getName())
                .build();
        
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
	
	/*
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleDefaultException(ResourceNotFoundException rfnException) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Internal Error")
                .detail(rfnException.getMessage())
                .developerMessage(rfnException.getClass().getName())
                .build();
        
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    */
	
}
