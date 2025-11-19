# EducationHub

## Overview
EducationHub is a Java-based application designed to provide educational services.

## Package Structure
The project is organized into the following packages:

- **config**: Contains configuration classes for the application, including security and caching.
- **controller**: Handles HTTP requests and responses.
- **dto**: Contains Data Transfer Objects used for transferring data between layers.
- **exception**: Manages custom exceptions and error handling.
- **filter**: Includes filters for processing requests, such as JWT authentication filters.
- **handler**: Contains global exception handlers.
- **mapper**: Provides mapping logic between entities and DTOs.
- **model**: Defines the data models/entities used in the application.
- **repository**: Interfaces for database operations using JPA.
- **service**: Contains the business logic of the application.
- **util**: Utility classes for common functionality.

## Prerequisites
- Java 17 or higher
- Maven 3.8+
- A database (e.g., MySQL, PostgreSQL) if required by the application

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd EducationHub
   ```
3. Build the project:
   ```bash
   ./mvnw clean install
   ```
4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Configuration
Configuration files are located in the `src/main/resources` directory:
- `application.properties`: Default configuration
- `application-dev.properties`: Development environment configuration
- `application-prod.properties`: Production environment configuration

## Libraries and Frameworks Used
- **Spring Boot**: For building the application.
- **Spring Security**: For securing the application.
- **Spring Cache (Caffeine)**: For caching JWT tokens.
- **JPA (Hibernate)**: For database interactions.
- **OpenAPI/Swagger**: For API documentation.


