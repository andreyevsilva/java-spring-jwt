package com.example.demo.handler;

import java.io.IOException;
import java.time.OffsetDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.example.demo.error.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

//Register in SecurityConfig
@Component
public class AuthorizationAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		
				// custom error response class used across my project
				ErrorDetails errorDetailsResponse = ErrorDetails.builder()
											       .timestamp(OffsetDateTime.now())
											       .status(HttpStatus.FORBIDDEN.value())
											       .title("Error Authentication")
											       .detail(accessDeniedException.getMessage())
											       .developerMessage(accessDeniedException.getClass().getName())
											       .build();
										
					ObjectMapper objectMapper = new ObjectMapper();
					try {
						response.getWriter().write(objectMapper.writeValueAsString(errorDetailsResponse));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
		
	}

}
