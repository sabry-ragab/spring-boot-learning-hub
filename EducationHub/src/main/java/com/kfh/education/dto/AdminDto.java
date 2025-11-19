package com.kfh.education.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminDto {

	@NotBlank(message = "Username is required")
	@Size(max = 100, message = "Username must be at most 100 characters")
	private String username;

	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;

}