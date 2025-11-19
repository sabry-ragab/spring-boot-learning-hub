package com.kfh.education.service;

import java.util.List;
import java.util.Set;

import com.kfh.education.dto.StudentDto;

public interface StudentService {

	StudentDto createStudent(StudentDto studentDto);

	void allocateCourses(Long studentId, Set<Long> courseIds);

	void updateStudentCourses(Long studentId, Set<Long> courseIds);

	List<StudentDto> getAllStudents();

	List<StudentDto> getStudentsByCourseIds(List<Long> courseIds);

	void deleteStudent(Long studentId);
}