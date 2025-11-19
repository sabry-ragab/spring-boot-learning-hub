package com.kfh.education.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.dto.StudentCoursesDto;
import com.kfh.education.dto.StudentDto;
import com.kfh.education.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	
	@PostMapping
	public ResponseEntity<StudentDto> createStudent(
			@Valid @RequestBody StudentDto studentDto) {
		StudentDto createdStudent = studentService.createStudent(studentDto);
		return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
	}

	@PostMapping("/{studentId}/courses")
	public ResponseEntity<?> addCoursesToStudent(@PathVariable Long studentId,
			@RequestBody StudentCoursesDto request) {
		studentService.allocateCourses(studentId, request.getCourseIds());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{studentId}/courses")
	public ResponseEntity<?> updateStudentCourses(@PathVariable Long studentId,
			@RequestBody StudentCoursesDto request) {
		studentService.updateStudentCourses(studentId, request.getCourseIds());
		return ResponseEntity.noContent().build();
	}
	
    @GetMapping
    public ResponseEntity<List<StudentDto>> getStudentsByCourseIds(
            @RequestParam(required = false) List<Long> courseIds) {

        if (courseIds == null || courseIds.isEmpty()) {
            return ResponseEntity.ok(studentService.getAllStudents());
        }

        List<StudentDto> students = studentService.getStudentsByCourseIds(courseIds);
        return ResponseEntity.ok(students);
    }

	@DeleteMapping("/{studentId}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
		studentService.deleteStudent(studentId);
		return ResponseEntity.noContent().build();
	}
}