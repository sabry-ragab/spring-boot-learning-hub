package com.example.learnspringboottesting.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.learnspringboottesting.exception.EmailAlreadyExistException;
import com.example.learnspringboottesting.exception.UserNotFoundException;
import com.example.learnspringboottesting.model.User;
import com.example.learnspringboottesting.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Long create(User user) {
		try {
			user.setId(null); // Ensure the ID is null for new users
			User createdUser = userRepository.save(user);
			return createdUser.getId();
		} catch (DataIntegrityViolationException e) { // Handle email uniqueness violation
			throw new EmailAlreadyExistException();
		}
	}

	public void update(Long id, User user) {
		if (id == null || !userRepository.existsById(id)) {
			throw new UserNotFoundException();
		}
		try {
			user.setId(id); // Set the ID for the existing user
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) { // Handle email uniqueness violation
			throw new EmailAlreadyExistException();
		}
	}

	public void delete(Long id) {
		User existingUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
		userRepository.delete(existingUser);
	}

	public User get(Long id) {
		return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
	}

	public Page<User> find(int page, int size, String sortBy, boolean desc) {
		Sort sort = desc ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return userRepository.findAll(pageable);
	}

}
