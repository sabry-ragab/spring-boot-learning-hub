package com.example.learnspringboottesting.exception;

public class EmailAlreadyExistException extends RuntimeException {

	public EmailAlreadyExistException() {
		super("Email already exists");
	}

}
