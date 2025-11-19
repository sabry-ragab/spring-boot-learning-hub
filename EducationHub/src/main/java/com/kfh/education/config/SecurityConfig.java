package com.kfh.education.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kfh.education.filter.JwtTokenFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtTokenFilter jwtTokenFilter;
	
	public static final String[] PUBLIC_ENDPOINTS = {
	    "/auth/login",
	    "/api/admins",
	    "/v3/api-docs",
	    "/v3/api-docs/**",
	    "/swagger-ui",
	    "/swagger-ui/**",
	    "/swagger-ui.html",
	    "/swagger-resources",
	    "/swagger-resources/**",
	    "/webjars/**"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(PUBLIC_ENDPOINTS)
				.permitAll()
				.anyRequest()
				.authenticated())
			.addFilterBefore(jwtTokenFilter,
					UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
