package com.example.criteriaapidemo.service;

import com.example.criteriaapidemo.entity.Employee;
import com.example.criteriaapidemo.filter.EmployeeCriteriaFilter;
//import com.example.criteriaapidemo.querydsl.EmployeePredicateBuilder;
//import com.example.criteriaapidemo.repository.EmployeeQueryDSLRepository;
import com.example.criteriaapidemo.repository.EmployeeRepository;
import com.example.criteriaapidemo.specification.EmployeeSpecification;
//import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
//    private final EmployeeQueryDSLRepository employeeQueryDSLRepository;

    /**
     * Search employees based on criteria filter with pagination and sorting using JPA Criteria API
     *
     * @param filter the employee criteria filter containing search parameters, pagination, and sorting
     * @return Page of employees matching the criteria
     */
    public Page<Employee> searchEmployees(EmployeeCriteriaFilter filter) {
        // Create Sort object
        Sort.Direction direction = Sort.Direction.fromString(filter.getSortDirection().toUpperCase());
        Sort sort = Sort.by(direction, filter.getSortBy());

        // Create Pageable object
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

        // Build specification and execute search
        return employeeRepository.findAll(
                EmployeeSpecification.filterByCriteria(filter),
                pageable
        );
    }

    /**
     * Search employees based on criteria filter with pagination and sorting using QueryDSL
     *
     * @param filter the employee criteria filter containing search parameters, pagination, and sorting
     * @return Page of employees matching the criteria
     */
    public Page<Employee> searchEmployeesByQueryDSL(EmployeeCriteriaFilter filter) {
        // Create Sort object
        Sort.Direction direction = Sort.Direction.fromString(filter.getSortDirection().toUpperCase());
        Sort sort = Sort.by(direction, filter.getSortBy());

        // Create Pageable object
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

        // Build QueryDSL predicate and execute search
//        Predicate predicate = EmployeePredicateBuilder.build(filter);

//        return employeeQueryDSLRepository.findAll(predicate, pageable);
        return employeeRepository.findAll(
                EmployeeSpecification.filterByCriteria(filter),
                pageable
        );
    }

}

