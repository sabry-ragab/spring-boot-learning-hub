package com.kfh.education.exception;

public class CourseAlreadyAllocatedException extends RuntimeException {
    public CourseAlreadyAllocatedException(String message) {
        super(message);
    }
}