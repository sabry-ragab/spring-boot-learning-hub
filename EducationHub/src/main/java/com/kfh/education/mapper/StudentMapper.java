package com.kfh.education.mapper;

import org.springframework.stereotype.Component;

import com.kfh.education.dto.StudentDto;
import com.kfh.education.model.Student;

@Component
public class StudentMapper extends AbstractMapper<StudentDto, Student> {
	@Override
	public Class<StudentDto> getDtoClass() {
		return StudentDto.class;
	}

	@Override
	public Class<Student> getEntityClass() {
		return Student.class;
	}

}
