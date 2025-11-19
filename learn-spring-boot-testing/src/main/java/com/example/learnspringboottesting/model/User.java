package com.example.learnspringboottesting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "users")
public class User {
	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty(message = "Name cannot be empty")
	@Column(nullable = false)
	private String name;

	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Email should be valid")
	@Column(unique = true, nullable = false)
	private String email;

}
