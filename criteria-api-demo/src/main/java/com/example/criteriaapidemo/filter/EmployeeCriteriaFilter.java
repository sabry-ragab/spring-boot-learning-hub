package com.example.criteriaapidemo.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCriteriaFilter {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Double minSalary;

    private Double maxSalary;

    private Long departmentId;

    private String departmentName;

    // Pagination
    private Integer page = 0;

    private Integer size = 10;

    // Sorting
    private String sortBy = "id";

    private String sortDirection = "ASC"; // ASC or DESC

}

