## University System API

A Spring Boot REST API for managing professors, students and courses.
The project demonstrates clean layered architecture, JPA entity relationships,
DTO mapping with MapStruct, database versioning with Flyway and unit testing with Mockito.

## Features

- Full CRUD operations for Professors, Students and Courses
- One-to-Many and Many-to-Many JPA relationships
- DTO-based architecture (no direct entity exposure)
- Automatic database migrations with Flyway
- Layered architecture (Controller → Service → Repository)
- Transaction-safe business logic
- MapStruct for compile-time object mapping
- Unit testing with JUnit 5 and Mockito
- Swagger/OpenAPI documentation
- Code quality enforced with Checkstyle

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.3.1
- **Database:** MySQL 8+
- **ORM:** Spring Data JPA + Hibernate
- **Migrations:** Flyway
- **Mapping:** MapStruct 1.6.3
- **Testing:** JUnit 5, Mockito
- **Build Tool:** Maven
- **API Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Code Quality:** Checkstyle
- **Boilerplate Reduction:** Lombok

## Architecture Overview

The project follows a strict layered architecture to ensure separation of concerns and maintainability:

- **Controller Layer** – Exposes REST endpoints and handles HTTP requests/responses.
- **Service Layer** – Contains business logic and transaction management.
- **Repository Layer** – Handles data access using Spring Data JPA.
- **Domain Layer** – Contains JPA entities and database mappings.
- **DTO Layer** – Used to transfer data between layers without exposing entities.
- **Mapper Layer** – Uses MapStruct to automatically map between entities and DTOs.

This structure ensures loose coupling, testability and clear responsibility separation across the application.

## Project Structure

```
src/
├── main/
│   ├── java/com/university/university_system/
│   │   ├── controller/
│   │   ├── domain/
│   │   ├── dto/
│   │   ├── mapper/
│   │   ├── repository/
│   │   └── service/
│   └── resources/
│       ├── application.properties
│       └── db/migration/
└── test/
    └── java/com/university/university_system/
        └── service/

```

## Database Model & Relationships

The system is based on a relational database design with the following relationships:

- A **Professor** can teach multiple **Courses** (One-to-Many relationship).
- A **Course** belongs to one **Professor** (Many-to-One relationship).
- A **Student** can enroll in multiple **Courses**, and each **Course** can have multiple **Students** (Many-to-Many relationship via `student_course` join table).

These relationships are fully managed using JPA annotations, ensuring referential integrity and consistent database state throughout the application.

## API Endpoints

The API exposes RESTful endpoints for managing professors, students, and courses.

---

### Professors — `/api/professors`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/create` | Create a new professor |
| GET    | `/{id}`   | Get professor by ID |
| PUT    | `/update` | Update professor details |
| PUT    | `/update-courses` | Update professor's courses |
| DELETE | `/{id}`   | Delete professor by ID |
| DELETE | `/list`   | Delete multiple professors |

---

### Students — `/api/students`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/create` | Create a new student |
| GET    | `/{id}`   | Get student by ID |
| PUT    | `/update` | Update student details |
| PUT    | `/update-courses` | Update student's courses |
| DELETE | `/{id}`   | Delete student by ID |
| DELETE | `/list`   | Delete multiple students |

---

### Courses — `/api/courses`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/create` | Create a new course |
| GET    | `/{id}`   | Get course by ID |
| PUT    | `/update` | Update course details |
| PUT    | `/update-professor` | Assign professor to course |
| DELETE | `/{id}`   | Delete course by ID |
| DELETE | `/list`   | Delete multiple courses |

## Getting Started

### Prerequisites

Make sure you have installed:

- Java 21+
- Maven 3.8+
- MySQL 8+

---

### Database Setup

Create a MySQL database:

```sql 
CREATE DATABASE uni_db;

```
---

### Configuration

Update your `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/uni_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=none
spring.flyway.enabled=true

```
---

### Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`.  
Swagger UI is available at `http://localhost:8080/swagger-ui.html`.

## Future Improvements

- Add Spring Security with JWT authentication
- Add pagination and filtering for large datasets
- Implement global exception handling (@ControllerAdvice)
- Add Docker support for containerization
- Add integration tests for API endpoints

## Project Impact

This project simulates a real university management system by handling professors, students, and course
enrollment, providing a structured and scalable backend solution for managing academic data efficiently.

## Project Motivation

This project was built to practice and demonstrate real-world backend development skills using Spring Boot,
focusing on clean architecture, database design and REST API development.