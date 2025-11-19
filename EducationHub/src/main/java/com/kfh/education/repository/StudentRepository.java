package com.kfh.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.kfh.education.model.Student;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmailIgnoreCase(String email);

	List<Student> findDistinctByCourseIdsIn(List<Long> courseIds);
}