# 🌍 Online Language School – Spring Boot REST API

This is a Spring Boot REST API application for managing an online language school. It supports operations for managing students, courses, teachers (employees), static content pages, contact messages, and user authentication.

🌐 Frontend:  https://easyeng.netlify.app/

---

## ✅ Features

- Full CRUD support for:
    - Students
    - Courses
    - Employees (Teachers)
    - Contact messages
    - Static page content
- User registration and login with encrypted passwords
- Relationship management:
    - Many-to-Many between Students and Courses
    - One-to-Many between Employee and Courses
- In-memory H2 database (or external MySQL)
- JSON serialization with cycle handling (`@JsonManagedReference`, `@JsonBackReference`)
- Basic HTTP authentication (Spring Security)
- Swagger UI available

---

## 👥 Authentication & Users
- `POST /api/auth/register` – Register a new user
- `POST /api/auth/login` – Login with email and password
  
User entity fields:
- `id`, `username`, `email`, `password (encrypted)`, `fullName`, `role`, `enabled`

## 🗂️ Entities Overview

### 🧑‍🎓 Student
- `id`, `firstName`, `lastName`, `email`
- Enrolled in many courses (Many-to-Many)

### 📘 Course
- `id`, `language`, `level`, `description`, `price`, `durationWeeks`
- Linked to one teacher (Employee)
- Has many students (Many-to-Many)

### 👩‍🏫 Employee (Teacher)
- `id`, `firstName`, `lastName`, `email`, `experienceYears`, `hourlyRate`
- Teaches multiple courses (One-to-Many)

### 💬 ContactMessage
- `id`, `name`, `email`, `message`, `sentAt`

### 🧾 PageContent
- `id`, `title`, `content`
### 👤 User
- `id`, `username`, `email`, `password (encrypted)`, `fullName`, `role`, `enabled`

---

## 🚀 How to Run

1. Clone the project:
   ```bash
   git clone https://github.com/your-name/language-school-api.git
   cd language-school-api
   ```

2. Run the application with Maven:
   ```bash
   mvn spring-boot:run
   ```

3. The application will be available at:
   ```
   http://localhost:8080/
   ```

---

## 🔗 REST API Endpoints
### 🌐 Home
| Method | Endpoint                      | Description                   |
|--------|-------------------------------|-------------------------------|
| GET    | `/`                           | Welcome message with link to Swagger              |

### 👥 Auth
| Method | Endpoint                       | Description                   |
|--------|--------------------------------|-------------------------------|
| POST   | `/api/auth/register`           | Register a new user            |
| POST    | `/api/auth/login`              | Login with email/password       |

### 🧑‍🎓 Students
| Method | Endpoint                        | Description                   |
|--------|----------------------------------|-------------------------------|
| GET    | `/api/students`                 | Get all students              |
| GET    | `/api/students/{id}`            | Get student by ID             |
| POST   | `/api/students`                 | Create new student            |
| PUT    | `/api/students/{id}`            | Update existing student       |
| DELETE | `/api/students/{id}`            | Delete student                |
| POST   | `/api/students/{id}/courses/{courseId}` | Enroll student in course |

### 📘 Courses
| Method | Endpoint              | Description              |
|--------|------------------------|--------------------------|
| GET    | `/api/courses`        | Get all courses          |
| GET    | `/api/courses/{id}`   | Get course by ID         |
| POST   | `/api/courses`        | Create new course        |
| PUT    | `/api/courses/{id}`   | Update course            |
| DELETE | `/api/courses/{id}`   | Delete course            |

### 👩‍🏫 Employees (Teachers)
| Method | Endpoint                   | Description                   |
|--------|-----------------------------|-------------------------------|
| GET    | `/api/employees`          | Get all employees             |
| GET    | `/api/employees/{id}`     | Get employee by ID            |
| POST   | `/api/employees`          | Create new employee           |
| PUT    | `/api/employees/{id}`     | Update existing employee      |
| DELETE | `/api/employees/{id}`     | Delete employee               |

### 💬 Contact Messages
| Method | Endpoint               | Description                  |
|--------|--------------------------|------------------------------|
| GET    | `/api/contact`         | Get all contact messages     |
| GET    | `/api/contact/{id}`    | Get a specific message       |
| POST   | `/api/contact`         | Submit a new message         |
| DELETE | `/api/contact/{id}`    | Delete a message             |

### 📟 Static Pages
| Method | Endpoint             | Description                |
|--------|------------------------|----------------------------|
| GET    | `/api/pages`         | Get all static pages       |
| GET    | `/api/pages/{id}`    | Get a specific page        |
| POST   | `/api/pages`         | Create a new page          |
| DELETE | `/api/pages/{id}`    | Delete a page              |

---

## 📦 Sample JSON Payloads

### Create Student
```json
{
  "firstName": "Alice",
  "lastName": "Johnson",
  "email": "alice@example.com"
}
```
### Register User
```json
{
  "username": "michael",
  "email": "michael@example.com",
  "password": "qwerty123",
  "fullName": "Michael Mustermann"
}
```
### Login User
```json
{
  "email": "michael@example.com",
  "password": "qwerty123"
}
```


### Create Course
```json
{
  "language": "German",
  "level": "Beginner",
  "description": "Intro to German",
  "price": 120,
  "durationWeeks": 4,
  "teacher": {
    "id": 2
  }
}
```

### Create Employee
```json
{
  "firstName": "John",
  "lastName": "Smith",
  "email": "john@example.com",
  "experienceYears": 5,
  "hourlyRate": 25
}
```

### Submit Contact Message
```json
{
  "name": "Visitor",
  "email": "visitor@example.com",
  "message": "I have a question about the German course."
}
```

---

## ⚙️ Configuration

- Remote MySQL DB: `192.168.0.17:3306/my_db`
- User: `remoteadmin`, Password: `springcourse`
- Hibernate auto-DDL enabled
- Basic Auth: `michael:qwerty` (in `application.properties`)
- Swagger UI: http://localhost:8080/swagger-ui/index.html#/
- Active profile: macmini
---

## 📁 Project Structure (src/main/java)
```
spring_data_rest
├── config/
├── controller/
├── dao/
├── dto/
├── entity/
└── SpringDataRestApplication.java
```

---

## 🔒 Security

- All `/api/**` endpoints are protected with Basic Auth
- Swagger & Actuator are publicly available
- CSRF protection disabled

---

## 🧑‍💼 Collaborating

- Currently working on:
    - Improved validation and error handling
    - Full frontend integration via https://easyeng.netlify.app/
    - New API endpoints and functionality
---
## 📃 License

Free to use for learning and educational purposes.
