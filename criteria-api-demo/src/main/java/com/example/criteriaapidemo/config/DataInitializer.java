package com.example.criteriaapidemo.config;

import com.example.criteriaapidemo.entity.Department;
import com.example.criteriaapidemo.entity.Employee;
import com.example.criteriaapidemo.repository.DepartmentRepository;
import com.example.criteriaapidemo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        initializeDepartments();
        initializeEmployees();
    }

    private void initializeDepartments() {
        Department dept1 = new Department();
        dept1.setCode("ENG");
        dept1.setName("Engineering");
        dept1.setCreatedAt(System.currentTimeMillis());

        Department dept2 = new Department();
        dept2.setCode("SAL");
        dept2.setName("Sales");
        dept2.setCreatedAt(System.currentTimeMillis());

        Department dept3 = new Department();
        dept3.setCode("HR");
        dept3.setName("Human Resources");
        dept3.setCreatedAt(System.currentTimeMillis());

        departmentRepository.saveAll(java.util.List.of(dept1, dept2, dept3));
    }

    private void initializeEmployees() {
        Department engineering = departmentRepository.findByCode("ENG").orElse(null);
        Department sales = departmentRepository.findByCode("SAL").orElse(null);
        Department hr = departmentRepository.findByCode("HR").orElse(null);

        // Create sample employees
        Employee emp1 = new Employee();
        emp1.setFirstName("John");
        emp1.setLastName("Doe");
        emp1.setEmail("john.doe@example.com");
        emp1.setPhone("555-0001");
        emp1.setSalary(85000.0);
        emp1.setDepartment(engineering);
        emp1.setCreatedAt(System.currentTimeMillis());

        Employee emp2 = new Employee();
        emp2.setFirstName("Jane");
        emp2.setLastName("Smith");
        emp2.setEmail("jane.smith@example.com");
        emp2.setPhone("555-0002");
        emp2.setSalary(95000.0);
        emp2.setDepartment(engineering);
        emp2.setCreatedAt(System.currentTimeMillis());

        Employee emp3 = new Employee();
        emp3.setFirstName("Michael");
        emp3.setLastName("Johnson");
        emp3.setEmail("michael.johnson@example.com");
        emp3.setPhone("555-0003");
        emp3.setSalary(75000.0);
        emp3.setDepartment(sales);
        emp3.setCreatedAt(System.currentTimeMillis());

        Employee emp4 = new Employee();
        emp4.setFirstName("Sarah");
        emp4.setLastName("Williams");
        emp4.setEmail("sarah.williams@example.com");
        emp4.setPhone("555-0004");
        emp4.setSalary(70000.0);
        emp4.setDepartment(sales);
        emp4.setCreatedAt(System.currentTimeMillis());

        Employee emp5 = new Employee();
        emp5.setFirstName("David");
        emp5.setLastName("Brown");
        emp5.setEmail("david.brown@example.com");
        emp5.setPhone("555-0005");
        emp5.setSalary(65000.0);
        emp5.setDepartment(hr);
        emp5.setCreatedAt(System.currentTimeMillis());

        Employee emp6 = new Employee();
        emp6.setFirstName("Emily");
        emp6.setLastName("Davis");
        emp6.setEmail("emily.davis@example.com");
        emp6.setPhone("555-0006");
        emp6.setSalary(72000.0);
        emp6.setDepartment(hr);
        emp6.setCreatedAt(System.currentTimeMillis());

        employeeRepository.saveAll(List.of(emp1, emp2, emp3, emp4, emp5, emp6));
    }

}

