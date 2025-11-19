package com.kfh.service;

import java.util.ArrayList;
import java.util.List;
import jakarta.jws.WebService;

@WebService(endpointInterface = "com.kfh.service.CourseService")
public class CourseServiceImpl implements CourseService {
    private static final List<Course> courses = new ArrayList<>();

    @Override
    public void addCourse(Course course) {
        courses.add(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }
}
