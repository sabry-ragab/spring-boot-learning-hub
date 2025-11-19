package com.kfh.service;

import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public interface CourseService {
    @WebMethod
    void addCourse(Course course);

    @WebMethod
    List<Course> getAllCourses();
}
