package com.example.learnspringboottesting.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.example.learnspringboottesting.model.User;
import com.example.learnspringboottesting.repository.UserRepository;

@Tag("integration")
@ActiveProfiles("integrationtest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FullHTTPIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		userRepository.deleteAll();
	}

	@Test
	void createUser_shouldReturn201AndPersistUser() {
		// Arrange
		User user = new User(null, "John", "john@example.com");

		// Act
		ResponseEntity<Void> response = restTemplate
			.postForEntity("/users", user, Void.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getHeaders().getLocation()).isNotNull();

		List<User> users = userRepository.findAll();
		assertThat(users).hasSize(1);
		assertThat(users.get(0).getEmail()).isEqualTo("john@example.com");
	}

	@Test
	void getUser_shouldReturnUser_whenExists() {
		// Arrange
		User user = new User(null, "Sarah", "sarah@example.com");
		user = userRepository.save(user);

		// Act
		ResponseEntity<User> response = restTemplate
			.getForEntity("/users/" + user.getId(), User.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getEmail())
			.isEqualTo("sarah@example.com");
		assertThat(response.getBody().getName()).isEqualTo("Sarah");
	}

	@Test
	void updateUser_shouldReturn204AndUpdateUser() {
		// Arrange
		User user = new User(null, "Old", "old@example.com");
		user = userRepository.save(user);

		User updatedUser = new User(user.getId(), "New", "new@example.com");

		HttpEntity<User> request = new HttpEntity<>(updatedUser);

		// Act
		ResponseEntity<Void> response = restTemplate
			.exchange("/users/" + user.getId(), HttpMethod.PUT, request,
					Void.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		User saved = userRepository.findById(user.getId()).orElseThrow();
		assertThat(saved.getEmail()).isEqualTo("new@example.com");
	}

	@Test
	void deleteUser_shouldReturn204AndDeleteUser() {
		// Arrange
		User user = new User(null, "To Delete", "toDelete@example.com");
		user = userRepository.save(user);

		// Act
		ResponseEntity<Void> response = restTemplate
			.exchange("/users/" + user.getId(), HttpMethod.DELETE, null,
					Void.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		assertThat(userRepository.findById(user.getId())).isEmpty();
	}

	@Test
	void createUser_shouldReturn400_whenNameIsNull() {
		// Arrange
		User invalidUser = new User(null, null, "invalid@example.com");

		// Act
		ResponseEntity<String> response = restTemplate
			.postForEntity("/users", invalidUser, String.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).contains("name", "Name cannot be empty");
	}

	@Test
	void updateUser_shouldReturn400_whenNameIsNull() {
		// Arrange
		User existing = new User(null, "Valid", "valid@example.com");
		existing = userRepository.save(existing);

		User invalidUpdate = new User(null, null, "updated@example.com");
		HttpEntity<User> request = new HttpEntity<>(invalidUpdate);

		// Act
		ResponseEntity<String> response = restTemplate
			.exchange("/users/" + existing.getId(), HttpMethod.PUT, request,
					String.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).contains("name", "Name cannot be empty");
	}

	@Test
	void getUser_shouldReturn404_whenUserNotFound() {
		// Act
		ResponseEntity<String> response = restTemplate
			.getForEntity("/users/99999", String.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).contains("User not found");
	}

	@Test
	void findUsers_shouldReturnPaginatedResults() {
		// Arrange
		userRepository.save(new User(null, "User1", "user1@example.com"));
		userRepository.save(new User(null, "User2", "user2@example.com"));
		userRepository.save(new User(null, "User3", "user3@example.com"));

		// Act
		ResponseEntity<String> response = restTemplate
			.getForEntity("/users?page=0&size=2&sortBy=name&desc=false",
					String.class);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody())
			.contains("user1@example.com", "user2@example.com");
		assertThat(response.getBody()).doesNotContain("user3@example.com");
	}

}
