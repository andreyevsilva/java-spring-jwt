package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import com.example.demo.filter.JWTAuthenticationFilter;
import com.example.demo.filter.JWTAuthorizationFilter;
import com.example.demo.handler.AuthenticationEntryPointHandler;
import com.example.demo.handler.AuthorizationAccessDeniedHandler;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthenticationService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final AuthenticationService authenticationService;
	

	@Autowired
	public SecurityConfig(AuthenticationService authenticationService,UserRepository userRepository) {
		this.authenticationService = authenticationService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
				.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
			.and()
				.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPointHandler()).accessDeniedHandler(new AuthorizationAccessDeniedHandler())
            .and()	
				// add jwt filters (1. authentication, 2. authorization)
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), this.authenticationService))
			.authorizeRequests()
	            .antMatchers(HttpMethod.POST, "/signup").permitAll()
			.anyRequest().authenticated();
			
			
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.authenticationProvider());
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.authenticationService);

		return daoAuthenticationProvider;
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}
