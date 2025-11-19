package com.kfh.education.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentDto {
	private Long id;

	@NotBlank(message = "English name is required")
	@Size(max = 100, message = "English name must be at most 100 characters")
	private String nameEn;

	@NotBlank(message = "Arabic name is required")
	@Size(max = 100, message = "Arabic name must be at most 100 characters")
	private String nameAr;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;

	@Size(max = 11, message = "Telephone must be at most 11 characters")
	private String telephone;

	@Size(max = 255, message = "Address must be at most 255 characters")
	private String address;
}