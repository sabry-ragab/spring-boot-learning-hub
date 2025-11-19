package com.example.learnspringboottesting.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.learnspringboottesting.exception.EmailAlreadyExistException;
import com.example.learnspringboottesting.exception.UserNotFoundException;
import com.example.learnspringboottesting.model.User;
import com.example.learnspringboottesting.repository.UserRepository;
import com.example.learnspringboottesting.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
	public void create_whenUserIsValid_returnsUserId() {
		// Arrange
		User user = new User(null, "testName", "testEmail@example.com");
		User mockedUserWithId = new User(1L, "testName", "testEmail@example.com");
		when(userRepository.save(user)).thenReturn(mockedUserWithId);

		// Act
		Long id = userService.create(user);

		// Assert
		assertThat(id).isEqualTo(1L);
	}

	@Test
	public void create_whenEmailAlreadyExists_throwsEmailAlreadyExistException() {
		// Arrange
		User user = new User(null, "testName", "testEmail@example.com");
		when(userRepository.save(user)).thenThrow(new DataIntegrityViolationException("Email already exists"));

		// Act & Assert
		assertThatThrownBy(() -> {
			userService.create(user);
		}).isInstanceOf(EmailAlreadyExistException.class);
	}

	@Test
	public void update_whenUserExists_updatesUser() {
		// Arrange
		Long userId = 1L;
		User existingUser = new User(userId, "testName", "testEmail@example.com");
		when(userRepository.existsById(userId)).thenReturn(true);

		// Act & Assert
		Assertions.assertDoesNotThrow(() -> {
			userService.update(userId, existingUser);
		});
		verify(userRepository).save(existingUser);
	}

	@Test
	public void update_whenUserDoesNotExist_throwsUserNotFoundException() {
		// Arrange
		Long userId = 1L;
		User user = new User(userId, "testName", "testEmail@example.com");
		when(userRepository.existsById(userId)).thenReturn(false);

		// Act & Assert
		assertThatThrownBy(() -> {
			userService.update(userId, user);
		}).isInstanceOf(UserNotFoundException.class);
	}

	@Test
	public void update_whenUserIdIsNull_throwsUserNotFoundException() {
		// Arrange
		User user = new User(1L, "testName", "testEmail@example.com");

		// Act & Assert
		assertThatThrownBy(() -> {
			userService.update(null, user);
		}).isInstanceOf(UserNotFoundException.class);
	}

	@Test
	public void update_whenEmailAlreadyExists_throwsEmailAlreadyExistException() {
		// Arrange
		Long userId = 1L;
		User existingUser = new User(userId, "testName", "testEmail@example.com");
		when(userRepository.existsById(userId)).thenReturn(true);
		when(userRepository.save(existingUser)).thenThrow(new DataIntegrityViolationException("Email already exists"));

		// Act & Assert
		assertThatThrownBy(() -> {
			userService.update(userId, existingUser);
		}).isInstanceOf(EmailAlreadyExistException.class);

	}

	@Test
	public void delete_whenUserExists_deletesUser() {
		// Arrange
		Long userId = 1L;
		User existingUser = new User(userId, "testName", "testEmail@example.com");
		when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));

		// Act & Assert
		Assertions.assertDoesNotThrow(() -> {
			userService.delete(userId);
		});
		verify(userRepository).delete(existingUser);
	}

	@Test
	public void delete_whenUserDoesNotExist_throwsUserNotFoundException() {
		// Arrange
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> {
			userService.delete(userId);
		}).isInstanceOf(UserNotFoundException.class);
	}

	@Test
	public void get_whenUserExists_returnsUser() {
		// Arrange
		Long userId = 1L;
		User existingUser = new User(userId, "testName", "testEmail@example.com");
		when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));

		// Act
		User user = userService.get(userId);

		// Assert
		assertThat(user).isEqualTo(existingUser);
	}

	@Test
	public void get_whenUserDoesNotExist_throwsUserNotFoundException() {
		// Arrange
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> {
			userService.get(userId);
		}).isInstanceOf(UserNotFoundException.class);
	}

	@Test
	public void find_withAscendingSort_returnsPageOfUsers() {
		// Arrange
		int page = 0;
		int size = 10;
		String sortBy = "id";
		boolean desc = false;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
		Page<User> userPage = Page.empty(pageable);
		when(userRepository.findAll(pageable)).thenReturn(userPage);

		// Act
		Page<User> result = userService.find(page, size, sortBy, desc);

		// Assert
		assertThat(result).isEqualTo(userPage);
		verify(userRepository).findAll(pageable);
	}

	@Test
	public void find_withDescendingSort_returnsPageOfUsers() {
		// Arrange
		int page = 0;
		int size = 10;
		String sortBy = "id";
		boolean desc = true;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
		Page<User> userPage = Page.empty(pageable);
		when(userRepository.findAll(pageable)).thenReturn(userPage);

		// Act
		Page<User> result = userService.find(page, size, sortBy, desc);

		// Assert
		assertThat(result).isEqualTo(userPage);
		verify(userRepository).findAll(pageable);
	}

}
