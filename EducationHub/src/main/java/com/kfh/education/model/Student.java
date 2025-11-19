package com.kfh.education.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nameEn;

	@Column(nullable = false)
	private String nameAr;

	@Column(nullable = false, unique = true)
	private String email;

	private String telephone;

	private String address;

	@ElementCollection
	@CollectionTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_id"))
	@Column(name = "course_id")
	private Set<Long> courseIds = new HashSet<>();

}
