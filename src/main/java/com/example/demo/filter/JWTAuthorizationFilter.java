package com.example.demo.filter;

import java.io.IOException;
import java.time.OffsetDateTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.demo.error.ErrorDetails;
import com.example.demo.security.JWTConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private final UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
	}
	
		
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(JWTConstants.HEADER_STRING);
		if (header == null || !header.startsWith(JWTConstants.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request, response);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request,HttpServletResponse response) {

		String token = request.getHeader(JWTConstants.HEADER_STRING);

		if (token == null) {
			return null;
		}

		String email = validateToken(token,response);
		
		if (email == null) {
			return null;
		}
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

		return email != null ? new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
				: null;
	}

	private String validateToken(final String token,HttpServletResponse response) {
		String email = null;
		try {
			email = Jwts.parser().setSigningKey(JWTConstants.SECRET)
					.parseClaimsJws(token.replace(JWTConstants.TOKEN_PREFIX, "")).getBody().getSubject();
			
			return email;
		} catch (SignatureException e) {
			throw new SignatureException("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			throw new SignatureException("Invalid JWT signature.");
		} catch (ExpiredJwtException e) {
			throw new SignatureException("Expired JWT token.");
		} catch (UnsupportedJwtException e) {
			throw new SignatureException("Unsupported JWT token.");
		} catch (IllegalArgumentException e) {
			throw new SignatureException("JWT claims string is empty.");
		}
	}
}
