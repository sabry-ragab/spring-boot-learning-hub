# Architecture Diagram

## Component Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                        HTTP CLIENT                              │
│              GET /api/employees/search?...                      │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                      EmployeeController                         │
│   • Receives query parameters                                   │
│   • Builds EmployeeCriteriaFilter                               │
│   • Calls EmployeeService.searchEmployees()                     │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    EmployeeService                              │
│   • searchEmployees(EmployeeCriteriaFilter)                     │
│   • Creates Sort and Pageable objects                           │
│   • Calls EmployeeRepository.findAll()                          │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                   EmployeeRepository                            │
│   • extends JpaRepository<Employee, Long>                       │
│   • extends JpaSpecificationExecutor<Employee>                  │
│   • findAll(Specification, Pageable)                            │
└────────────────────────────┬────────────────────────────────────┘
                             │
          ┌──────────────────┼──────────────────┐
          │                  │                  │
          ▼                  ▼                  ▼
┌────────────────────┐ ┌──────────────────┐ ┌─────────────────┐
│ EmployeeSpecif.    │ │   Pageable       │ │      Sort       │
│ filterByCriteria() │ │ • page = 0       │ │ • field: "id"   │
│                    │ │ • size = 10      │ │ • direction:    │
│ Criteria API Query │ │                  │ │   ASC/DESC      │
│ Building:          │ │ Pagination       │ │                 │
│ • WHERE clauses    │ │ Metadata         │ │ Sort Metadata   │
│ • JOINs            │ │                  │ │                 │
│ • LIKE queries     │ │                  │ │                 │
│ • Range queries    │ │                  │ │                 │
└────────────────────┘ └──────────────────┘ └─────────────────┘
          │
          │ Combined into SQL Query
          │
          ▼
┌─────────────────────────────────────────────────────────────────┐
│                      Hibernate/JPA                              │
│   Generates and executes SQL query against database             │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                      H2 Database                                │
│                                                                 │
│  ┌──────────────────────┐      ┌──────────────────────┐        │
│  │   employees table    │      │   departments table  │        │
│  ├──────────────────────┤      ├──────────────────────┤        │
│  │ id (PK)              │      │ id (PK)              │        │
│  │ firstName            │      │ name                 │        │
│  │ lastName             │      │ location             │        │
│  │ email                │      │ createdAt            │        │
│  │ phone                │      │                      │        │
│  │ salary               │      │ (1:M relationship)   │        │
│  │ department_id (FK) ──┼──────→ id                   │        │
│  │ createdAt            │      │                      │        │
│  └──────────────────────┘      └──────────────────────┘        │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼ Results
┌─────────────────────────────────────────────────────────────────┐
│              Page<Employee> with metadata                       │
│  • content: [Employee, Employee, ...]                           │
│  • totalElements: X                                             │
│  • totalPages: Y                                                │
│  • size: 10                                                     │
│  • pageable: {...}                                              │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼ JSON Response
┌─────────────────────────────────────────────────────────────────┐
│                        HTTP CLIENT                              │
│                     (Pagination + Data)                         │
└─────────────────────────────────────────────────────────────────┘
```

## Class Diagram

```
┌──────────────────────────┐
│      Employee (Entity)   │
├──────────────────────────┤
│ - id: Long               │
│ - firstName: String      │
│ - lastName: String       │
│ - email: String          │
│ - phone: String          │
│ - salary: Double         │
│ - department: Department │ ◄─────┐
│ - createdAt: Long        │       │ ManyToOne
└──────────────────────────┘       │
                                   │
                    ┌──────────────┴──┐
                    │ OneToMany        │
                    │                  │
                    │
        ┌───────────▼──────────────┐
        │  Department (Entity)     │
        ├──────────────────────────┤
        │ - id: Long               │
        │ - name: String           │
        │ - location: String       │
        │ - createdAt: Long        │
        └──────────────────────────┘

┌──────────────────────────────────────┐
│ EmployeeCriteriaFilter (Filter DTO)  │
├──────────────────────────────────────┤
│ SEARCH FIELDS:                       │
│ - firstName: String                  │
│ - lastName: String                   │
│ - email: String                      │
│ - phone: String                      │
│ - minSalary: Double                  │
│ - maxSalary: Double                  │
│ - departmentId: Long                 │
│ - departmentName: String             │
│ - minCreatedAt: Long                 │
│ - maxCreatedAt: Long                 │
│                                      │
│ PAGINATION:                          │
│ - page: Integer (default: 0)         │
│ - size: Integer (default: 10)        │
│                                      │
│ SORTING:                             │
│ - sortBy: String (default: "id")     │
│ - sortDirection: String (ASC|DESC)   │
└──────────────────────────────────────┘

┌─────────────────────────────────┐
│   EmployeeRepository            │
├─────────────────────────────────┤
│ extends                         │
│ • JpaRepository<E, Long>        │
│ • JpaSpecificationExecutor<E>   │
│                                 │
│ Methods:                        │
│ • findAll(Spec, Pageable)       │
└─────────────────────────────────┘

┌────────────────────────────────────┐
│   EmployeeSpecification            │
├────────────────────────────────────┤
│ + filterByCriteria(filter)         │
│   : Specification<Employee>        │
│                                    │
│ Builds WHERE clauses:              │
│ • LIKE queries (firstName, etc.)   │
│ • Range queries (salary, dates)    │
│ • JOIN queries (departmentName)    │
└────────────────────────────────────┘

┌────────────────────────────────────┐
│   EmployeeService                  │
├────────────────────────────────────┤
│ - employeeRepository               │
│                                    │
│ + searchEmployees(filter)          │
│   : Page<Employee>                 │
└────────────────────────────────────┘

┌─────────────────────────────────────────┐
│   EmployeeController                    │
├─────────────────────────────────────────┤
│ GET /api/employees/search               │
│ (Query Params: firstName, lastName...)  │
│                                         │
│ Returns: ResponseEntity<Page<Employee>> │
└─────────────────────────────────────────┘
```

## Data Flow Example

### Scenario: Search engineers with salary > 80000

**1. HTTP Request:**
```
GET /api/employees/search?firstName=&minSalary=80000&departmentName=Engineering&sortBy=salary&sortDirection=DESC&page=0&size=5
```

**2. EmployeeController:**
```
Receives parameters → builds EmployeeCriteriaFilter:
{
  firstName: null,
  lastName: null,
  email: null,
  phone: null,
  minSalary: 80000.0,
  maxSalary: null,
  departmentId: null,
  departmentName: "Engineering",
  page: 0,
  size: 5,
  sortBy: "salary",
  sortDirection: "DESC"
}
```

**3. EmployeeService:**
```
Creates Pageable: PageRequest(page=0, size=5, Sort(salary, DESC))
Calls: employeeRepository.findAll(Specification, Pageable)
```

**4. EmployeeSpecification:**
```
Builds Criteria Query:
WHERE
  salary >= 80000.0
  AND department.name LIKE '%engineering%' (case-insensitive)
ORDER BY salary DESC
LIMIT 5 OFFSET 0
```

**5. Generated SQL (approximate):**
```sql
SELECT e.* FROM employees e
LEFT JOIN departments d ON e.department_id = d.id
WHERE e.salary >= 80000.0
  AND LOWER(d.name) LIKE '%engineering%'
ORDER BY e.salary DESC
LIMIT 5 OFFSET 0
```

**6. Response:**
```json
{
  "content": [
    {
      "id": 2,
      "firstName": "Jane",
      "lastName": "Smith",
      "email": "jane.smith@example.com",
      "phone": "555-0002",
      "salary": 95000.0,
      "department": { "id": 1, "name": "Engineering", ... },
      "createdAt": 1700000000000
    },
    {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "phone": "555-0001",
      "salary": 85000.0,
      "department": { "id": 1, "name": "Engineering", ... },
      "createdAt": 1700000000000
    }
  ],
  "pageable": { "pageNumber": 0, "pageSize": 5, ... },
  "totalElements": 2,
  "totalPages": 1,
  "number": 0,
  ...
}
```

## Query Building Logic

The EmployeeSpecification combines filters with AND logic:

```
WHERE (filter1) AND (filter2) AND (filter3) AND ...
```

All string filters use LIKE with wildcards:
```
firstName LIKE '%john%' (case-insensitive)
```

Range filters use comparison operators:
```
salary >= minSalary
salary <= maxSalary
```

Joins are used for relationship queries:
```
LEFT JOIN department ON employee.department_id = department.id
```

