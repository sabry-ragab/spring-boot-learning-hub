package com.kfh.education.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.dto.AdminDto;
import com.kfh.education.dto.LoginResponseDto;
import com.kfh.education.service.AuthenticationService;

@RestController
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	public AuthenticationController(
			AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody AdminDto adminDto) {
		LoginResponseDto loginResponseDto = authenticationService
			.login(adminDto.getUsername(), adminDto.getPassword());
		return ResponseEntity.ok(loginResponseDto);
	}

	@PostMapping("/api/logout")
	public ResponseEntity<?> logout(
			@RequestHeader("Authorization") String authorizationHeader) {
		authenticationService.logout(authorizationHeader);
		return ResponseEntity.ok("Logged out");
	}
}