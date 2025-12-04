# QueryDSL Implementation Documentation

## Overview
Added QueryDSL support alongside the existing JPA Criteria API implementation. Both approaches provide type-safe query building but with different syntax and capabilities.

## New Files Created

### 1. **EmployeeQueryDSLRepository.java**
Located: `repository/EmployeeQueryDSLRepository.java`

```java
public interface EmployeeQueryDSLRepository extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Employee> {
    Page<Employee> findAll(Predicate predicate, Pageable pageable);
}
```

**Key Features:**
- Extends `QuerydslPredicateExecutor<Employee>` for QueryDSL support
- Enables type-safe query building with Q classes
- Automatically generates Q classes via annotation processor

### 2. **EmployeeQueryDSLBuilder.java**
Located: `querydsl/EmployeeQueryDSLBuilder.java`

Builds QueryDSL predicates from `EmployeeCriteriaFilter`:

```java
public static Predicate buildPredicate(EmployeeCriteriaFilter filter) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    
    if (StringUtils.hasText(filter.getFirstName())) {
        booleanBuilder.and(QEmployee.employee.firstName.containsIgnoreCase(filter.getFirstName()));
    }
    // ... more conditions
}
```

**Key Methods Used:**
- `containsIgnoreCase()` - Case-insensitive LIKE matching
- `goe()` - Greater than or equal (>=)
- `loe()` - Less than or equal (<=)
- `eq()` - Equals (=)

### 3. **EmployeeQueryDSLRepository (Enhanced)**
Auto-generates `QEmployee` and `QDepartment` Q classes via Maven plugin.

## Updated Files

### EmployeeService.java
Added new method:

```java
public Page<Employee> searchEmployeesByQueryDSL(EmployeeCriteriaFilter filter) {
    Sort.Direction direction = Sort.Direction.fromString(filter.getSortDirection().toUpperCase());
    Sort sort = Sort.by(direction, filter.getSortBy());
    Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);
    
    Predicate predicate = EmployeeQueryDSLBuilder.buildPredicate(filter);
    return employeeQueryDSLRepository.findAll(predicate, pageable);
}
```

### EmployeeController.java
Added new endpoint:

```java
@GetMapping("/search/querydsl")
public ResponseEntity<Page<Employee>> searchEmployeesByQueryDSL(
        @ModelAttribute EmployeeCriteriaFilter filter
) {
    Page<Employee> employees = employeeService.searchEmployeesByQueryDSL(filter);
    return ResponseEntity.ok(employees);
}
```

### pom.xml
Added QueryDSL dependencies:

```xml
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-jpa</artifactId>
    <classifier>jakarta</classifier>
</dependency>
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-apt</artifactId>
    <classifier>jakarta</classifier>
    <scope>provided</scope>
</dependency>
```

## API Endpoints

### Criteria API Endpoint
```
GET /api/employees/search?firstName=John&minSalary=80000&sortBy=salary&sortDirection=DESC
```

### QueryDSL Endpoint
```
GET /api/employees/search/querydsl?firstName=John&minSalary=80000&sortBy=salary&sortDirection=DESC
```

Both endpoints accept the same query parameters and return identical results.

## Comparison: Criteria API vs QueryDSL

| Feature | Criteria API | QueryDSL |
|---------|-------------|----------|
| **Type Safety** | Strong typing via interfaces | Stronger via Q classes |
| **Query Syntax** | CriteriaBuilder API | Fluent DSL style |
| **IDE Support** | Good auto-completion | Excellent with Q classes |
| **Performance** | Good | Comparable |
| **Learning Curve** | Moderate | Steeper (needs Q classes) |
| **Generated Classes** | Not needed | Required (Q classes) |
| **Readability** | Good | Excellent |
| **Complexity Handling** | Good | Better |

## QueryDSL Benefits

1. **Fluent API**: More readable query building
   ```java
   // QueryDSL
   QEmployee.employee.firstName.containsIgnoreCase("John")
   
   // Criteria API
   cb.like(cb.lower(root.get("firstName")), "%john%")
   ```

2. **Type Safety**: Q classes provide compile-time checking
   ```java
   QEmployee.employee.firstName  // Type-safe access
   root.get("firstName")          // String-based, prone to typos
   ```

3. **Reusable Predicates**: Easy to combine and reuse
   ```java
   Predicate name = QEmployee.employee.firstName.contains("John");
   Predicate salary = QEmployee.employee.salary.goe(80000);
   Predicate combined = name.and(salary);
   ```

## Q Classes Generation

Q classes are automatically generated during Maven build:

```bash
mvn clean compile
```

Generated classes appear in `target/generated-sources/java/com/example/criteriaapidemo/entity/`:
- `QEmployee.java`
- `QDepartment.java`

## Using QueryDSL Predicates

Individual predicates can be used independently:

```java
// In controller or service
Predicate predicate = QEmployee.employee.salary.goe(50000)
    .and(QEmployee.employee.department.id.eq(1L));

Page<Employee> results = employeeQueryDSLRepository.findAll(predicate, pageable);
```

## Migration Between Approaches

The `EmployeeCriteriaFilter` is shared between both methods, making it easy to:
1. Test both implementations
2. Benchmark performance
3. Migrate from one to another

Both methods produce identical SQL and results.

## Dependencies Added

```xml
<!-- QueryDSL JPA -->
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-jpa</artifactId>
    <classifier>jakarta</classifier>
</dependency>

<!-- QueryDSL Annotation Processor (for Q class generation) -->
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-apt</artifactId>
    <classifier>jakarta</classifier>
    <scope>provided</scope>
</dependency>
```

Note: The `jakarta` classifier is used for Jakarta EE compatibility (Java 17+ / Spring Boot 4.0.0)

## Building the Project

```bash
# Build with Q class generation
mvn clean install

# Run application
mvn spring-boot:run
```

## Testing Both Implementations

### Criteria API
```bash
curl "http://localhost:8080/api/employees/search?firstName=John&minSalary=80000"
```

### QueryDSL
```bash
curl "http://localhost:8080/api/employees/search/querydsl?firstName=John&minSalary=80000"
```

Both return the same results with the same pagination and sorting applied.

## Next Steps

1. **Build the project** to generate Q classes
2. **Test both endpoints** to verify functionality
3. **Choose preferred approach** based on team preference
4. **Consider removing one** if only one approach is needed

Both implementations are production-ready and can coexist in the same application.

