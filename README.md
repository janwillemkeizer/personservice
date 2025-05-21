# Person Service

A Spring Boot application with H2 in-memory database.

## Prerequisites

- Java 21
- Maven

## Getting Started

1. Clone the repository

2. Build and run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will start on port 8090.

## Database

The application uses an H2 in-memory database that is automatically populated with sample data on startup using Flyway
migrations.

You can access the H2 console at http://localhost:8090/h2-console with the following credentials:

- JDBC URL: jdbc:h2:mem:persondb
- Username: sa
- Password: (empty)

# Swagger

http://localhost:8090/swagger-ui/index.html#/

## Stopping the Application

Stop the Spring Boot application by pressing `Ctrl+C` in the terminal. 