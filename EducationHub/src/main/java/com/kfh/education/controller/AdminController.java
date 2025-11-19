package com.kfh.education.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.dto.AdminDto;
import com.kfh.education.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@PostMapping
	public ResponseEntity<AdminDto> create(@Valid @RequestBody AdminDto adminDto) {
		AdminDto createdAdminDto = adminService.create(adminDto);
		return new ResponseEntity<>(createdAdminDto, HttpStatus.CREATED);
	}
}
