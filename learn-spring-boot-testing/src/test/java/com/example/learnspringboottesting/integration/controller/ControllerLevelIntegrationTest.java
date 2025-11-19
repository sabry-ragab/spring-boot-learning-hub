package com.example.learnspringboottesting.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import com.example.learnspringboottesting.exception.UserNotFoundException;
import com.example.learnspringboottesting.model.User;
import com.example.learnspringboottesting.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
@Tag("integration")
class ControllerLevelIntegrationTest {

	@Autowired
	MockMvcTester mockMvcTester;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		userRepository.deleteAll();
	}

	@Test
	void createUser_shouldReturn201Created() throws JsonProcessingException {
		var user = new User(null, "John", "john@example.com");
		String jsonBody = objectMapper.writeValueAsString(user);

		MvcTestResult result = mockMvcTester
			.post()
			.uri("/users")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonBody)
			.exchange();

		assertThat(result)
			.hasStatus(HttpStatus.CREATED)
			.containsHeader("Location");

		assertThat(userRepository.findAll()).hasSize(1);
	}

	@Test
	void createUser_shouldReturn400_whenNameIsNull()
			throws JsonProcessingException {
		var invalidUser = new User(null, null, "invalid@example.com");
		String jsonBody = objectMapper.writeValueAsString(invalidUser);

		MvcTestResult result = mockMvcTester
			.post()
			.uri("/users")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonBody)
			.exchange();

		assertThat(result)
			.hasStatus(HttpStatus.BAD_REQUEST)
			.bodyText()
			.contains("Name cannot be empty");
	}

	@Test
	void getUserById_shouldReturnUser() {
		var saved = userRepository
			.save(new User(null, "Alice", "alice@example.com"));

		MvcTestResult result = mockMvcTester
			.get()
			.uri("/users/" + saved.getId())
			.accept(MediaType.APPLICATION_JSON)
			.exchange();

		assertThat(result)
			.hasStatus(HttpStatus.OK)
			.bodyJson()
			.convertTo(User.class)
			.satisfies(user -> {
				assertThat(user.getName()).isEqualTo("Alice");
				assertThat(user.getEmail()).isEqualTo("alice@example.com");
			});
	}

	@Test
	void getUserById_shouldReturn404_whenNotFound() {
		MvcTestResult result = mockMvcTester
			.get()
			.uri("/users/9999")
			.accept(MediaType.APPLICATION_JSON)
			.exchange();

		assertUserNotFound(result);
	}

	@Test
	void findUsers_shouldReturnPaginatedResults() {
		userRepository.save(new User(null, "User1", "user1@example.com"));
		userRepository.save(new User(null, "User2", "user2@example.com"));
		userRepository.save(new User(null, "User3", "user3@example.com"));

		MvcTestResult result = mockMvcTester
			.get()
			.uri("/users?page=0&size=2&sortBy=name&desc=false")
			.accept(MediaType.APPLICATION_JSON)
			.exchange();

		assertThat(result)
	    .hasStatus(HttpStatus.OK)
	    .bodyJson()
	    .extractingPath("$.content")
	    .asInstanceOf(InstanceOfAssertFactories.LIST)
	    .hasSize(2);

	}

	@Test
	void updateUser_shouldReturn204NoContent() throws JsonProcessingException {
		var saved = userRepository
			.save(new User(null, "Old Name", "old@example.com"));
		var updated = new User(null, "New Name", "new@example.com");
		String jsonBody = objectMapper.writeValueAsString(updated);

		MvcTestResult result = mockMvcTester
			.put()
			.uri("/users/" + saved.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonBody)
			.exchange();

		assertThat(result).hasStatus(HttpStatus.NO_CONTENT);

		var updatedUser = userRepository.findById(saved.getId()).orElseThrow();
		assertThat(updatedUser.getName()).isEqualTo("New Name");
		assertThat(updatedUser.getEmail()).isEqualTo("new@example.com");
	}

	@Test
	void updateUser_shouldReturn404_whenNotFound()
			throws JsonProcessingException {
		var user = new User(null, "Ghost", "ghost@example.com");
		String jsonBody = objectMapper.writeValueAsString(user);

		MvcTestResult result = mockMvcTester
			.put()
			.uri("/users/9999")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonBody)
			.exchange();

		assertUserNotFound(result);
	}

	@Test
	void deleteUser_shouldReturn204NoContent() {
		var saved = userRepository
			.save(new User(null, "To Delete", "delete@example.com"));

		MvcTestResult result = mockMvcTester
			.delete()
			.uri("/users/" + saved.getId())
			.exchange();

		assertThat(result).hasStatus(HttpStatus.NO_CONTENT);
		assertThat(userRepository.existsById(saved.getId())).isFalse();
	}

	@Test
	void deleteUser_shouldReturn404_whenNotFound() {
		MvcTestResult result = mockMvcTester
			.delete()
			.uri("/users/9999")
			.exchange();

		assertUserNotFound(result);
	}

	private void assertUserNotFound(MvcTestResult result) {
		// We can assert both the HTTP status and the exception thrown
		assertThat(result)
			.hasStatus(HttpStatus.NOT_FOUND)
			.bodyJson()
			.isLenientlyEqualTo("""
					{
					  "error": "User not found"
					}
					""");

		assertThat(result)
			.failure()
			.isInstanceOf(UserNotFoundException.class)
			.hasMessage("User not found");
	}
}
