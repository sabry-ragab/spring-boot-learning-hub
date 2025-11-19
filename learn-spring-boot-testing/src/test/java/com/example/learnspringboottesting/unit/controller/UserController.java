package com.example.learnspringboottesting.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import com.example.learnspringboottesting.controller.UserController;
import com.example.learnspringboottesting.model.User;
import com.example.learnspringboottesting.service.UserService;
import com.example.learnspringboottesting.util.UriBuilder;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private UriBuilder uriBuilder;

	@InjectMocks
	private UserController userController;

	@Test
	void create_returns201Created() {
		// Arrange
		User user = new User();
		user.setName("Alice");
		when(userService.create(user)).thenReturn(1L);
		URI uri = URI.create("/users/1");
		when(uriBuilder.buildLocationUri(1L)).thenReturn(uri);

		// Act
		ResponseEntity<Void> response = userController.create(user);

		// Assert
		assertThat(response.getStatusCode().value()).isEqualTo(201);
		assertThat(response.getHeaders().getLocation()).isEqualTo(uri);

		verify(userService).create(user);
	}

	@Test
	void update_returns204NoContent() {
		// Arrange
		Long id = 1L;
		User user = new User();
		user.setName("Bob");

		// Act
		ResponseEntity<Void> response = userController.update(id, user);

		// Assert
		assertThat(response.getStatusCode().value()).isEqualTo(204);
		verify(userService).update(id, user);
	}

	@Test
	void delete_returns204NoContent() {
		// Arrange
		Long id = 1L;

		// Act
		ResponseEntity<Void> response = userController.delete(id);

		// Assert
		assertThat(response.getStatusCode().value()).isEqualTo(204);
		verify(userService).delete(id);
	}

	@Test
	void get_returns200OkAndUser() {
		// Arrange
		Long id = 1L;
		User user = new User(id, "Charlie", "test@example.com");

		when(userService.get(id)).thenReturn(user);

		// Act
		ResponseEntity<User> response = userController.get(id);

		// Assert
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).isEqualTo(user);

		verify(userService).get(id);
	}

	@Test
	void find_returnsPagedUsers() {
		// Arrange
		List<User> users = List.of(new User(1L, "John", "john@example.com"), new User(99L, "Jane", "jane@example.com"));
		Page<User> page = new PageImpl<>(users);

		when(userService.find(0, 10, "id", false)).thenReturn(page);

		// Act
		Page<User> result = userController.find(0, 10, "id", false);

		// Assert
		assertThat(result.getContent()).hasSize(2);
		assertThat(result.getContent()).isEqualTo(users);

		verify(userService).find(0, 10, "id", false);
	}
}
