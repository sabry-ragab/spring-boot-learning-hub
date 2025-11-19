package com.kfh.education.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kfh.education.dto.CourseDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
class CourseServiceImpl implements CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    
    @Override
    @Cacheable("courses")
    public List<CourseDto> getAllCourses() {
    	
    	// TODO: fetch from SOAP web service
    	
        logger.info("Fetching courses from method, not cache");
        
        return Arrays.asList(
            new CourseDto(1L, "Mathematics"),
            new CourseDto(2L, "Physics"),
            new CourseDto(3L, "Chemistry"),
            new CourseDto(4L, "Biology")
        );
    }
}