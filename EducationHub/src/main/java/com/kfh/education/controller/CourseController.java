package com.kfh.education.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.dto.CourseDto;
import com.kfh.education.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
	public List<CourseDto> getCourses() {
		return courseService.getAllCourses();
	}
}
