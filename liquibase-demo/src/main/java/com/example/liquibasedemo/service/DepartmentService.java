package com.example.liquibasedemo.service;

import com.example.liquibasedemo.model.CodeSequence;
import com.example.liquibasedemo.model.Department;
import com.example.liquibasedemo.repo.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final CodeGeneratorService codeGeneratorService;

    // Create
    public Department createDepartment(Department department) {
        department.setId(null); // Ensure ID is null for new entity
        String generatedCode = codeGeneratorService.generateNextCode(CodeSequence.DEPARTMENT);
        department.setCode(generatedCode);
        return departmentRepository.save(department);
    }

    // Read - Get by ID
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    // Read - Get all
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Read - Get by code
    public Department getDepartmentByCode(String code) {
        return departmentRepository.findByCode(code);
    }

}
