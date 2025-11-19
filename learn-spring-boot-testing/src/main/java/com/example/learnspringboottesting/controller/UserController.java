package com.example.learnspringboottesting.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.learnspringboottesting.model.User;
import com.example.learnspringboottesting.service.UserService;
import com.example.learnspringboottesting.util.UriBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final UriBuilder uriBuilder;

	public UserController(UserService userService, UriBuilder uriBuilder) {
		this.userService = userService;
		this.uriBuilder = uriBuilder;
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody User user) {
		Long id = userService.create(user);
		URI uri = uriBuilder.buildLocationUri(id);
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody User user) {
		userService.update(id, user);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> get(@PathVariable Long id) {
		User user = userService.get(id);
		return ResponseEntity.ok(user);
	}

	@GetMapping
	public Page<User> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "false") boolean desc) {

		return userService.find(page, size, sortBy, desc);
	}

}
