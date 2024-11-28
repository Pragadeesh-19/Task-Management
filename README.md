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
- 🏢 Department-based Task Organization
- 👥 Multi-user Task Assignment
- ✅ Comprehensive Input Validation
- 📝 Detailed Error Handling
- 🧪 Unit Test Coverage
- 📚 Swagger API Documentation

## 🔧 Technologies Used

- **Spring Boot** (3.3.5)
- **PostgreSQL** (Database)
- **Spring Security** with JWT Authentication
- **Spring Data JPA** for Database management
- **Docker** for running MySQL
- **JUnit 5** & **Mockito** for Unit Testing
- **IntelliJ IDEA** as IDE
- **Postman** for API Testing

## 📋 Prerequisites

- **Java**: JDK 17 or higher
- **DOcker**: Latest version
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
  "department": {
      "id": "uuid",
      "name": "string",
      "escription": "string"
  },
  "assignedUsers": [
      {
        "id": "UUID",
        "username": "string",
        "role": "ADMIN|USER"
      }
  ]
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}
```

###Department
```json
{
  "id": "UUID",
  "name": "string",
  "description": "string",
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}
```


### Entity Relationship Diagram

#### Core Entities
- **Task**: Represents a task with title, description, and status
- **Department**: Organizational unit that owns tasks
- **User**: System user who can be assigned to tasks

#### Relationships
- One Department can have many Tasks (1:*)
- One Department can have many Users (1:*)
- Many Tasks can be assigned to Many Users (*:*)

1. Entity-Relationship diagram:
  
      ![image](https://github.com/user-attachments/assets/271d25fc-65fa-49f4-bbcf-3f00fa7016ff)




## 🛠️ Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/task-management-api.git
   cd task-management-api
   ```

2.  **Start MySQL using Docker**
   ```bash
   docker-compose up -d
   ```

3. **Configuration**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/taskmanagement
   spring.datasource.username=root
   spring.datasource.password=${SPRING.DATASOURCE.PASSWORD}
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
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


## 🛠️ API Endpoints

| HTTP Method | Endpoint                          | Description               | Auth Required |
|-------------|-----------------------------------|---------------------------|---------------|
| `POST`      | `api/auth/signup`                 | Register new user         | ❌            |
| `POST`      | `api/auth/login`                  | Login and get JWT         | ❌            |
| `GET`       | `/tasks`                          | Get all tasks             | ✅            |
| `GET`       | `/tasks/{id}`                     | Get task by ID            | ✅            |
| `POST`      | `/tasks`                          | Create new task           | ✅            |
| `PUT`       | `/tasks/{id}`                     | Update existing task      | ✅            |
| `DELETE`    | `/tasks/{id}`                     | Delete task               | ✅            |
| `PATCH`     | `/tasks/{id}/complete`            | Mark task as complete     | ✅            |
| `POST`      | `/tasks/{id}/assign-users`        | Assign users to task      | ✅            |
| `POST`      | `/tasks/{id}/assign-department`   | Assign department to task | ✅            |
| `GET`       | `/api/departments`                | Get all departments       | ✅            |
| `POST`      | `/api/departments`                | Create department         | ✅            |
| `PUT`       | `/api/departments/{id}`           | Update department         | ✅            |
| `DELETE`    | `/api/departments/{id}`           | Delete department         | ✅            |

## 📁 Project Structure


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
|   |       ├── Mapper         # Entity-Dto mapping
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


## 🐳 Docker Support

The application uses Docker for MySQL database:

1. Start MySQL container:
   ```bash
   docker-compose up -d
   ```

2. Check container status:
   ```bash
   docker ps
   ```

3. Stop container:
   ```bash
   docker-compose down
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
