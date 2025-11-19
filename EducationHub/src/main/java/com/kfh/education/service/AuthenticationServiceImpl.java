package com.kfh.education.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.kfh.education.dto.LoginResponseDto;
import com.kfh.education.util.JwtTokenUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtCacheService jwtCacheService;
	private final JwtTokenUtil jwtTokenUtil;

	public AuthenticationServiceImpl(
			AuthenticationManager authenticationManager,
			UserDetailsService userDetailsService,
			JwtCacheService jwtCacheService, JwtTokenUtil jwtTokenUtil) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtCacheService = jwtCacheService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	public LoginResponseDto login(String username, String password) {
		authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(username,
					password));

		UserDetails userDetails = userDetailsService
			.loadUserByUsername(username);
		String token = jwtTokenUtil.generateToken(userDetails);

		jwtCacheService.put(username, token); // Cache the token

		return LoginResponseDto
			.builder()
			.token(token)
			.username(username)
			.build();
	}

	@Override
	public void logout(String authorizationHeader) {

		String token = (authorizationHeader != null
				&& authorizationHeader.startsWith("Bearer "))
						? authorizationHeader.substring(7)
						: null;

		// Remove the token from cache
		String username = jwtTokenUtil.extractUsername(token);
		jwtCacheService.remove(username);
	}
}