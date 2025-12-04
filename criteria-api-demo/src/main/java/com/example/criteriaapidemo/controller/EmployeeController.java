package com.example.criteriaapidemo.controller;

import com.example.criteriaapidemo.entity.Employee;
import com.example.criteriaapidemo.filter.EmployeeCriteriaFilter;
import com.example.criteriaapidemo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Search employees based on query parameters
     *
     * @param filter EmployeeCriteriaFilter containing all search parameters, pagination, and sorting
     * @return Page of employees matching the search criteria
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Employee>> searchEmployees(
            @ModelAttribute EmployeeCriteriaFilter filter
    ) {

        // Search employees
        Page<Employee> employees = employeeService.searchEmployees(filter);

        return ResponseEntity.ok(employees);
    }

    /**
     * Search employees based on query parameters using QueryDSL
     *
     * @param filter EmployeeCriteriaFilter containing all search parameters, pagination, and sorting
     * @return Page of employees matching the search criteria
     */
    @GetMapping("/search/querydsl")
    public ResponseEntity<Page<Employee>> searchEmployeesByQueryDSL(
            @ModelAttribute EmployeeCriteriaFilter filter
    ) {
        // Search employees using QueryDSL
        Page<Employee> employees = employeeService.searchEmployeesByQueryDSL(filter);

        return ResponseEntity.ok(employees);
    }

}

