//package com.example.criteriaapidemo.querydsl;
//
//import com.example.criteriaapidemo.entity.QEmployee;
//import com.example.criteriaapidemo.filter.EmployeeCriteriaFilter;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.types.Predicate;
//import org.springframework.util.StringUtils;
//
//public class EmployeePredicateBuilder {
//
//    private static final QEmployee employee = QEmployee.employee;
//
//    // Builder method that chains everything
//    public static Predicate build(EmployeeCriteriaFilter filter) {
//        BooleanBuilder builder = new BooleanBuilder();
//
//        builder.and(hasFirstName(filter.getFirstName()));
//        builder.and(hasLastName(filter.getLastName()));
//        builder.and(hasEmail(filter.getEmail()));
//        builder.and(hasPhone(filter.getPhone()));
//        builder.and(minSalary(filter.getMinSalary()));
//        builder.and(maxSalary(filter.getMaxSalary()));
//        builder.and(belongsToDepartment(filter.getDepartmentId()));
//        builder.and(departmentNameContains(filter.getDepartmentName()));
//
//        return builder;
//    }
//
//    // Individual reusable predicates
//    private static Predicate hasFirstName(String firstName) {
//        return StringUtils.hasText(firstName)
//                ? employee.firstName.containsIgnoreCase(firstName)
//                : null;
//    }
//
//    private static Predicate hasLastName(String lastName) {
//        return StringUtils.hasText(lastName)
//                ? employee.lastName.containsIgnoreCase(lastName)
//                : null;
//    }
//
//    private static Predicate hasEmail(String email) {
//        return StringUtils.hasText(email)
//                ? employee.email.containsIgnoreCase(email)
//                : null;
//    }
//
//    private static Predicate hasPhone(String phone) {
//        return StringUtils.hasText(phone)
//                ? employee.phone.containsIgnoreCase(phone)
//                : null;
//    }
//
//    private static Predicate minSalary(Double minSalary) {
//        return minSalary != null ? employee.salary.goe(minSalary) : null;
//    }
//
//    private static Predicate maxSalary(Double maxSalary) {
//        return maxSalary != null ? employee.salary.loe(maxSalary) : null;
//    }
//
//    private static Predicate belongsToDepartment(Long departmentId) {
//        return departmentId != null ? employee.department.id.eq(departmentId) : null;
//    }
//
//    private static Predicate departmentNameContains(String departmentName) {
//        return StringUtils.hasText(departmentName)
//                ? employee.department.name.containsIgnoreCase(departmentName)
//                : null;
//    }
//
//}