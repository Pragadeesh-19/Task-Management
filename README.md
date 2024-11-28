# ✨ Task Management REST API

A robust RESTful API for managing tasks built with Spring Boot. This API provides comprehensive task management capabilities with proper error handling, input validation, and authentication.

## 🚀 Features

### Core Functionality
- **Complete CRUD Operations** for task management
- **Task Status Tracking** (pending, in_progress, completed)
- **Automatic Timestamp Management** for task creation and updates
- **Due Date Management** for task scheduling

### Technical Features
- 🔐 JWT-based Authentication
- ✅ Comprehensive Input Validation
- 📝 Detailed Error Handling
- 🧪 Unit Test Coverage
- 📚 Swagger API Documentation

## 📑 Table of Contents

- [Technologies Used](#-technologies-used)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Running the Application](#-running-the-application)
- [API Endpoints](#%EF%B8%8F-api-endpoints)
- [Project Structure](#-project-structure)
- [Contributing](#-contributing)

## 🔧 Technologies Used

- **Spring Boot** (3.3.5)
- **PostgreSQL** (Database)
- **Spring Security** with JWT Authentication
- **Spring Data JPA** for Database management
- **JUnit 5** & **Mockito** for Unit Testing
- **IntelliJ IDEA** as IDE
- **Postman** for API Testing

## 📋 Prerequisites

- **Java**: JDK 17 or higher
- **PostgreSQL**: Version 12 or higher
- **Maven**: For dependency management

## 💾 Data Model

### Task
```json
{
  "id": "uuid",
  "title": "string",
  "description": "string",
  "dueDate": "yyyy-MM-dd",
  "status": "pending|in_progress|completed",
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}
```

## 🛠️ Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/task-management-api.git
   cd task-management-api
   ```

2. **Configure PostgreSQL**
   ```properties
   spring.datasource.url=${SPRING.DATASOURCE.URL}
   spring.datasource.username=${SPRING.DATASOURCE.USERNAME}
   spring.datasource.password=${SPRING.DATASOURCE.PASSWORD}
   spring.datasource.driver-class-name=org.postgresql.Driver
   ```

3. **Install Dependencies**
   ```bash
   mvn clean install
   ```

## 🚦 Running the Application

1. **Start the application**
   ```bash
   mvn spring-boot:run
   ```
   The API will be available at `http://localhost:8080`

2. **Access Swagger Documentation**
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

## 🛠️ API Endpoints

| HTTP Method | Endpoint                | Description              | Auth Required |
|-------------|------------------------|--------------------------|---------------|
| `POST`      | `api/auth/signup`      | Register new user        | ❌            |
| `POST`      | `api/auth/login`       | Login and get JWT       | ❌            |
| `GET`       | `/tasks`               | Get all tasks           | ✅            |
| `GET`       | `/tasks/{id}`          | Get task by ID          | ✅            |
| `POST`      | `/tasks`               | Create new task         | ✅            |
| `PUT`       | `/tasks/{id}`          | Update existing task    | ✅            |
| `DELETE`    | `/tasks/{id}`          | Delete task             | ✅            |
| `PATCH`     | `/tasks/{id}/complete` | Mark task as complete   | ✅            |

## 📁 Project Structure

```
src
├── main
│   ├── java
│   │   └── com.example.taskmanagement
│   │       ├── Config         # Configuration
│   │       ├── controller     # REST Controllers
│   │       ├── model          # JPA Entities
│   │       ├── DTO            # DTO classes
|   |       ├── Exception      # Exception classes
│   │       ├── repository     # DAO Layer
│   │       ├── service        # Business Logic
│   │       ├── Util           # JWT Utilities
│   │       ├── filter         # JWT Request filter
│   │       └── TaskManagementApplication.java
│   └── resources
│       └── application.properties    # Configuration
├── test
│   └── java
│       └── com.example.taskmanagement
│           └── service        # Unit Tests
```

## 🧪 Running Tests

Execute the test suite using:
```bash
mvn test
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature-name"
   ```
4. Push to branch:
   ```bash
   git push origin feature-name
   ```
5. Open a Pull Request

   Contributions are welcome! Please open an issue or submit a pull request for any changes or enhancements.


