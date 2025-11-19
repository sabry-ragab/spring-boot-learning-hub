package com.kfh.education.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.kfh.education.dto.StudentDto;
import com.kfh.education.exception.CourseAlreadyAllocatedException;
import com.kfh.education.exception.EmailAlreadyExistException;
import com.kfh.education.exception.StudentNotFoundException;
import com.kfh.education.mapper.StudentMapper;
import com.kfh.education.model.Student;
import com.kfh.education.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	private final StudentRepository studentRepository;
	private final StudentMapper studentMapper;

	public StudentServiceImpl(StudentRepository studentRepository,
			StudentMapper studentMapper) {
		this.studentRepository = studentRepository;
		this.studentMapper = studentMapper;
	}

	@Override
	public StudentDto createStudent(StudentDto studentDto) {
		Student studentWithSameEmail = studentRepository
			.findByEmailIgnoreCase(studentDto.getEmail())
			.orElse(null);
		if (studentWithSameEmail != null) {
			throw new EmailAlreadyExistException();
		}

		Student student = studentMapper.toEntity(studentDto);
		student.setId(null); // Ensure ID is null for new entity

		Student createdStudent = studentRepository.save(student);
		return studentMapper.toDto(createdStudent);
	}

	@Override
	public void deleteStudent(Long studentId) {
		if (!studentRepository.existsById(studentId)) {
			throw new StudentNotFoundException();
		}
		studentRepository.deleteById(studentId);
	}

	@Override
	public void allocateCourses(Long studentId, Set<Long> courseIds) {
		Student student = studentRepository
			.findById(studentId)
			.orElseThrow(() -> new StudentNotFoundException());

		for (Long courseId : courseIds) {
			if (student.getCourseIds().contains(courseId)) {
				throw new CourseAlreadyAllocatedException("Course with id "
						+ courseId + " is already allocated to the student.");
			}
		}

		student.getCourseIds().addAll(courseIds);
		studentRepository.save(student);
	}

	@Override
	public void updateStudentCourses(Long studentId, Set<Long> courseIds) {
		Student student = studentRepository
			.findById(studentId)
			.orElseThrow(() -> new StudentNotFoundException());

		student.getCourseIds().clear();
		student.getCourseIds().addAll(courseIds);
		studentRepository.save(student);
	}

	@Override
	public List<StudentDto> getAllStudents() {
		List<Student> students = studentRepository.findAll();
		return studentMapper.toDtoList(students);
	}

	@Override
	public List<StudentDto> getStudentsByCourseIds(List<Long> courseIds) {
		List<Student> students = studentRepository
			.findDistinctByCourseIdsIn(courseIds);
		return studentMapper.toDtoList(students);
	}

}