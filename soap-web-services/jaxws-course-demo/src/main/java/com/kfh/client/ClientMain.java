package com.kfh.client;

import java.util.List;

public class ClientMain {
	public static void main(String[] args) {
		CourseServiceImplService service = new CourseServiceImplService();
		CourseService port = service.getCourseServiceImplPort();

		// Call the service methods
		Course course1 = new Course();
		course1.setId(1);
		course1.setName("Mathematics");
		course1.setDescription("Basic Mathematics Course");
		port.addCourse(course1);

		Course course2 = new Course();
		course2.setId(2);
		course2.setName("Physics");
		course2.setDescription("Basic Physics Course");
		port.addCourse(course2);

		List<Course> response = port.getAllCourses();
		for (Course c : response) {
			System.out.println("Course ID: " + c.getId());
			System.out.println("Course Name: " + c.getName());
			System.out.println("Course Description: " + c.getDescription());
			System.out.println("---------------------------");
		}
	}
}
