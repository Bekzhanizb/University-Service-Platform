# University Service Platform

> A comprehensive service management system for universities built with Spring Boot

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## Table of Contents

- [About](#about)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Security](#security)
- [Team](#team)
- [License](#license)

---

## About

University Service Platform is a RESTful API application designed to streamline university service management. The system enables students to create service requests, register for events, and receive notifications, while administrators and teachers can manage requests, organize events, and monitor platform activities.

### Key Objectives

-  Centralized service request management
-  Event organization and registration
-  Real-time notification system
-  Role-based access control
-  Secure JWT authentication

---

##  Features

### For Students
-  Create and track service requests
-  Register for university events
-  Receive real-time notifications
-  Manage student profile
-  Comment on requests

### For Teachers
-  View and process service requests
-  Assign requests to specialists
-  Track request status
- ️ Create university events
-  Communicate via comments

### For Administrators
-  User management (create, delete, block)
- ️ Category management
-  System monitoring
-  Role assignment
-  Complete platform oversight

---

## ️ Tech Stack

### Backend
- **Framework:** Spring Boot 4.0.1
- **Security:** Spring Security + JWT
- **ORM:** Spring Data JPA + Hibernate
- **Database:** PostgreSQL 16
- **Migrations:** Liquibase
- **Mapper:** MapStruct 1.6.3
- **Build Tool:** Maven

### Testing
- **Unit Tests:** JUnit 5 + Mockito
- **API Testing:** Postman

### Development Tools
- **Java:** 17
- **Lombok:** Code generation
- **H2:** In-memory testing database

---

## ️ Architecture

### Layered Architecture

```
┌─────────────────────────────────────┐
│         REST Controllers            │  ← HTTP Layer
├─────────────────────────────────────┤
│            Services                 │  ← Business Logic
├─────────────────────────────────────┤
│          Repositories               │  ← Data Access
├─────────────────────────────────────┤
│           Database                  │  ← PostgreSQL
└─────────────────────────────────────┘
```

### Design Principles

- **Separation of Concerns:** Each layer has a specific responsibility
- **DTO Pattern:** Decoupling internal entities from API responses
- **Dependency Injection:** Spring IoC container manages dependencies
- **Repository Pattern:** Abstraction over data access logic

### Request Flow

```
HTTP Request → Controller → Service → Repository → Database
                    ↓           ↓
                  DTO      Entity (via Mapper)
```

---

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- PostgreSQL 15+
- Git

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/university-service-platform.git
cd university-service-platform
```

2. **Create PostgreSQL database**
```sql
CREATE DATABASE final_db;
CREATE USER postgres WITH PASSWORD '1234';
GRANT ALL PRIVILEGES ON DATABASE university_db TO postgres;
```

3. **Configure database connection**

Edit `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/university_db
    username: postgres
    password: 1234
```

4. **Build the project**
```bash
mvn clean install
```

5. **Run the application**
```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

### Docker Setup (Optional)

```bash
# Start PostgreSQL in Docker
docker run --name university-postgres \
  -e POSTGRES_PASSWORD=1234 \
  -e POSTGRES_DB=final_db \
  -p 5432:5432 \
  -d postgres:15
```

---

##  API Documentation

### Base URL
```
http://localhost:8080/api
```

### Authentication

All endpoints (except `/auth/register` and `/auth/login`) require JWT token:

```bash
Authorization: Bearer <jwt_token>
```

### Core Endpoints

#### Authentication
```http
POST /api/auth/register      # Register new user
POST /api/auth/login         # Login and get JWT token
GET  /api/auth/me            # Get current user info
PUT  /api/auth/change-password    # Change password
PUT  /api/auth/profile       # Update profile
```

#### Users (Admin only)
```http
GET    /api/users            # Get all users
GET    /api/users/{id}       # Get user by ID
POST   /api/users            # Create user
DELETE /api/users/{id}       # Delete user
PUT    /api/users/{id}/block # Block user
PUT    /api/users/{id}/unblock # Unblock user
```

#### Service Requests
```http
GET  /api/requests           # Get all requests (Teacher/Admin)
GET  /api/requests/my        # Get my requests (Student)
GET  /api/requests/{id}      # Get request by ID
POST /api/requests           # Create request (Student)
PUT  /api/requests/{id}/status # Update status (Teacher/Admin)
```

#### Events
```http
GET  /api/events             # Get all events
GET  /api/events/{id}        # Get event by ID
POST /api/events             # Create event (Teacher/Admin)
```

#### Event Participation
```http
POST /api/participants/events/{eventId}  # Register for event
GET  /api/participants/my                # Get my events
GET  /api/participants/events/{eventId}  # Get event participants
```

#### Notifications
```http
GET /api/notifications       # Get my notifications
PUT /api/notifications/{id}/read # Mark as read
```

### Example Requests

**Register:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "student@example.com",
    "password": "password123",
    "fullName": "John Doe",
    "role": "STUDENT"
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "student@example.com",
    "password": "password123"
  }'
```

**Create Request:**
```bash
curl -X POST http://localhost:8080/api/requests \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Need academic transcript",
    "description": "I need an official transcript for job application",
    "categoryId": 1
  }'
```

### Postman Collection

Import the Postman collection from `postman/UniversityServicePlatform.postman_collection.json` for easy API testing.

---

##  Testing

### Run Unit Tests
```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=AuthServiceImplTest

# With coverage report
mvn test jacoco:report
```

### Test Coverage

- **Services:** 8 test classes with 25+ test methods
- **Coverage:** ~85% of service layer
- **Framework:** JUnit 5 + Mockito

### Example Test
```java
@ExtendWith(MockitoExtension.class)
class ServiceRequestServiceImplTest {
    
    @Mock
    private ServiceRequestRepository requestRepository;
    
    @InjectMocks
    private ServiceRequestServiceImpl requestService;
    
    @Test
    void createRequest_Success() {
        ServiceRequestDTO dto = ServiceRequestDTO.builder()
            .title("Test Request")
            .build();
        
        ServiceRequestResponseDTO result = requestService.createRequest(dto);
        
        assertNotNull(result);
        verify(requestRepository).save(any());
    }
}
```

---

## Project Structure

```
src/
├── main/
│   ├── java/bekezhan/io/universityserviceplatform/
│   │   ├── config/
│   │   ├── security/              # Security configuration
│   │   ├── controller/            # REST Controllers
│   │   ├── dto/                   # Data Transfer Objects
│   │   ├── entity/                # JPA Entities
│   │   ├── exception/             # Exception handling
│   │   ├── mapper/                # MapStruct mappers
│   │   ├── repository/            # JPA Repositories
│   │   └── service/               # Business logic
│   │       ├── impl/              # Service implementations
│   │       └── [interfaces]       # Service interfaces
│   └── resources/
│       ├── application.yml        # Main configuration
│       └── db/changelog/          # Liquibase migrations
└── test/
    ├── java/                      # Unit tests
    └── resources/
```

---

##  Database Schema

### Core Entities (10 total)

#### Users & Profiles
- **users** - Base user information
- **student_profiles** - Student-specific data
- **teacher_profiles** - Teacher-specific data

#### Service Management
- **service_categories** - Request categories
- **service_requests** - Service requests
- **request_assignments** - Request assignments to teachers
- **request_comments** - Comments on requests

#### Events & Notifications
- **university_events** - University events
- **event_participants** - Event registrations
- **notifications** - User notifications

### Entity Relationships

```
users (1) ──→ (1) student_profiles
users (1) ──→ (1) teacher_profiles
users (1) ──→ (N) notifications

student_profiles (1) ──→ (N) service_requests
service_categories (1) ──→ (N) service_requests
service_requests (1) ──→ (1) request_assignments
service_requests (1) ──→ (N) request_comments

users (1) ──→ (N) university_events (created_by)
university_events (1) ──→ (N) event_participants
users (1) ──→ (N) event_participants
```

### ER Diagram

```
┌──────────────┐       ┌───────────────────┐
│    users     │───────│ student_profiles  │
└──────────────┘       └───────────────────┘
       │                        │
       │                        │
       │               ┌────────────────┐
       │               │service_requests│
       │               └────────────────┘
       │                        │
       │                        │
       │               ┌────────────────────┐
       ├───────────────│university_events   │
       │               └────────────────────┘
       │                        │
       │                        │
       │               ┌────────────────────┐
       └───────────────│event_participants  │
                       └────────────────────┘
```

---

## Security

### Authentication & Authorization

- **JWT (JSON Web Tokens)** for stateless authentication
- **BCrypt** password hashing
- **Role-based access control** with Spring Security

### Roles & Permissions

| Role | Permissions |
|------|-------------|
| **STUDENT** | Create requests, register for events, view own data |
| **TEACHER** | View all requests, update request status, create events |
| **ADMIN** | Full system access, user management, system configuration |

### Security Features

 JWT token expiration (1 hour)  
 Password encryption with BCrypt  
 User blocking/unblocking  
 Password change functionality  
 Profile editing with validation  
 Protected endpoints with `@PreAuthorize`  
 CORS configuration  
 SQL injection prevention (JPA)

### JWT Flow

```
1. User → POST /auth/login (email + password)
2. Server validates credentials
3. Server generates JWT token
4. Client stores token
5. Client sends token in Authorization header
6. JwtFilter validates token on each request
7. Spring Security grants access based on role
```

---

##  Team

**Project Members:**
- **1** - Izbassar Bekzhan
- **2** - Alibek Nurdaulet

**Roles Distribution:**
- Backend API: Services, Controllers, Repositories
- Security: JWT, Spring Security configuration
- Database: Liquibase migrations, schema design
- Testing: Unit tests, Postman collection
- Documentation: README, API docs

**Development Time:** ~32 hours over 7 days

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

**Made with ❤️ for Software Engineering course**