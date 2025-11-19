package com.example.learnspringboottesting.integration.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.example.learnspringboottesting.model.User;
import com.example.learnspringboottesting.repository.UserRepository;

@ActiveProfiles("integrationtest")
@DataJpaTest
public class UserRepositoryIntegrationTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void findById_whenUserExist_returnsUser() {
		// Arrange
		User user = new User(null, "testName", "test@example.com");
		userRepository.save(user);
		Long userId = user.getId();
		
		// Act
		User fetchedUser = userRepository.findById(userId).orElse(null);
		
		// Assert
		assertNotNull(fetchedUser);
		assertEquals("testName", fetchedUser.getName());
		assertEquals("test@example.com", fetchedUser.getEmail());
	}
	
	@Test
	public void findById_whenUserNotExist_returnsEmpty() {
		// Arrange
		Long userId = 1L; // Assuming this ID does not exist
		
		// Act
		Optional<User> optionalUser = userRepository.findById(userId);
		
		// Assert
		assertFalse(optionalUser.isPresent(), "User should not exist");
	}
	
	@Test
	public void save_whenNewUserIsValid_returnsUser() {
		// Arrange
		User user = new User(null, "testName", "test@example.com");
		
		// Act
		User savedUser = userRepository.save(user);
		
		// Assert
		assertNotNull(savedUser.getId(), "Saved user should have an ID");
		assertEquals("testName", savedUser.getName());
		assertEquals("test@example.com", savedUser.getEmail());
	}
	
	@Test
	public void save_withDuplicateEmail_throwsDataIntegrityException() {
		// Arrange
	    User user1 = new User(null, "Alice", "alice@example.com");
	    User user2 = new User(null, "Bob", "alice@example.com"); // same email

	    userRepository.save(user1);
	    userRepository.flush(); // force insert

	    // Act & Assert
	    assertThrows(DataIntegrityViolationException.class, () -> {
	        userRepository.save(user2);
	        userRepository.flush(); // trigger DB constraint
	    });
	}
	
	@Test
	public void save_whenUserAlreadyExist_updatesExistingUser() {
		// Arrange
	    User user = new User(null, "Alice", "alice@example.com");
	    userRepository.save(user);
	    userRepository.flush(); // force insert
	    user.setName("Alice Updated");
	    
	    // Act
	    User updatedUser = userRepository.save(user);
	    
	    // Assert
	    assertNotNull(updatedUser.getId(), "Updated user should have an ID");
	    assertEquals("Alice Updated", updatedUser.getName());
	    assertEquals("alice@example.com", updatedUser.getEmail());
	}
	
	@Test
    public void delete_existingUser_userIsDeleted() {
        // Arrange
		User user = new User(null, "Alice", "alice@example.com");
        User savedUser = userRepository.save(user);
        userRepository.flush(); // force insert to ensure the user is saved

        // Act
        userRepository.delete(savedUser);

        // Assert
        assertFalse(userRepository.existsById(savedUser.getId()));
    }

    @Test
    public void delete_nonExistingUser_noExceptionThrown() {
        // Arrange
    	User user = new User(999L, "Alice", "alice@example.com");
    	
        // Act & Assert
        assertDoesNotThrow(() -> {
        	userRepository.delete(user); // Deleting non-existing user
        });
    }
}
