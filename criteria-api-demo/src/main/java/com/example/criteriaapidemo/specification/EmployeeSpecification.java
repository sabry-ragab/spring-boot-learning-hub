package com.example.criteriaapidemo.specification;

import com.example.criteriaapidemo.entity.Department;
import com.example.criteriaapidemo.entity.Department_;
import com.example.criteriaapidemo.entity.Employee;
import com.example.criteriaapidemo.entity.Employee_;
import com.example.criteriaapidemo.filter.EmployeeCriteriaFilter;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class EmployeeSpecification {

    public static Specification<Employee> filterByCriteria(EmployeeCriteriaFilter filter) {
        return Specification.where(hasFirstName(filter.getFirstName()))
                .and(hasLastName(filter.getLastName()))
                .and(hasEmail(filter.getEmail()))
                .and(hasPhone(filter.getPhone()))
                .and(hasMinSalary(filter.getMinSalary()))
                .and(hasMaxSalary(filter.getMaxSalary()))
                .and(hasDepartmentId(filter.getDepartmentId()))
                .and(hasDepartmentName(filter.getDepartmentName()));
    }

    public static Specification<Employee> hasFirstName(String firstName) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(firstName)) {
                return cb.like(cb.lower(root.get(Employee.Fields.firstName)), "%" + firstName.toLowerCase() + "%");
            }
            return cb.conjunction();
        };
    }

    public static Specification<Employee> hasLastName(String lastName) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(lastName)) {
                return cb.like(cb.lower(root.get(Employee.Fields.lastName)), "%" + lastName.toLowerCase() + "%");
            }
            return cb.conjunction();
        };
    }

    public static Specification<Employee> hasEmail(String email) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(email)) {
                return cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
            }
            return cb.conjunction();
        };
    }

    public static Specification<Employee> hasPhone(String phone) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(phone)) {
                return cb.like(cb.lower(root.get("phone")), "%" + phone.toLowerCase() + "%");
            }
            return cb.conjunction();
        };
    }

    public static Specification<Employee> hasMinSalary(Double minSalary) {
        return (root, query, cb) -> {
            if (minSalary != null) {
                return cb.greaterThanOrEqualTo(root.get("salary"), minSalary);
            }
            return cb.conjunction();
        };
    }

    public static Specification<Employee> hasMaxSalary(Double maxSalary) {
        return (root, query, cb) -> {
            if (maxSalary != null) {
                return cb.lessThanOrEqualTo(root.get("salary"), maxSalary);
            }
            return cb.conjunction();
        };
    }

    public static Specification<Employee> hasDepartmentId(Long departmentId) {
        return (root, query, cb) -> {
            if (departmentId != null) {
                return cb.equal(root.get(Employee.Fields.department).get(Department.Fields.id), departmentId);
            }
            return cb.conjunction();
        };
    }

    public static Specification<Employee> hasDepartmentName(String departmentName) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(departmentName)) {
                return cb.like(cb.lower(root.get(Employee_.department).get(Department_.name)), "%" + departmentName.toLowerCase() + "%");
            }
            return cb.conjunction();
        };
    }
}

