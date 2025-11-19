package com.kfh.education.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.kfh.education.exception.CourseAlreadyAllocatedException;
import com.kfh.education.exception.EmailAlreadyExistException;
import com.kfh.education.exception.NoUserLoggedInException;
import com.kfh.education.exception.SessionExpiredException;
import com.kfh.education.exception.StudentNotFoundException;
import com.kfh.education.exception.UsernameAlreadyExistException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationErrors(
			MethodArgumentNotValidException ex) {
		List<Map<String, String>> fieldErrors = ex
			.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(error -> Map
				.of("field", error.getField(), "message",
						error.getDefaultMessage()))
			.toList();

		return ResponseEntity
			.badRequest()
			.body(Map.of("error", "Validation failed", "details", fieldErrors));
	}

	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<Object> handleBadCredentialsException(
			BadCredentialsException ex, WebRequest request) {
		Map<String, List<String>> body = new HashMap<>();
		body.put("errors", Collections.singletonList("Bad credentials"));
		return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(CourseAlreadyAllocatedException.class)
	protected ResponseEntity<Object> handleCourseAlreadyAllocatedException(
			CourseAlreadyAllocatedException ex, WebRequest request) {
		Map<String, List<String>> body = new HashMap<>();
		body.put("errors", Collections.singletonList(ex.getMessage()));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoUserLoggedInException.class)
	protected ResponseEntity<Object> handleNoUserLoggedInException(
			NoUserLoggedInException ex, WebRequest request) {
		Map<String, List<String>> body = new HashMap<>();
		body.put("errors", Collections.singletonList("No user logged in"));
		return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UsernameAlreadyExistException.class)
	protected ResponseEntity<Object> handleUsernameAlreadyExistException(
			UsernameAlreadyExistException ex, WebRequest request) {
		Map<String, List<String>> body = new HashMap<>();
		body
			.put("errors",
					Collections.singletonList("Username already exists"));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmailAlreadyExistException.class)
	protected ResponseEntity<Object> handleEmailAlreadyExistException(
			EmailAlreadyExistException ex, WebRequest request) {
		Map<String, List<String>> body = new HashMap<>();
		body.put("errors", Collections.singletonList("Email already exists"));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(StudentNotFoundException.class)
	protected ResponseEntity<Object> handleStudentNotFoundException(
			StudentNotFoundException ex, WebRequest request) {
		Map<String, List<String>> body = new HashMap<>();
		body.put("errors", Collections.singletonList("Student not found"));
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGenericException(Exception ex) {
		ex.printStackTrace(); // or use a logger
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(Map.of("error", "An unexpected error occurred"));
	}

}