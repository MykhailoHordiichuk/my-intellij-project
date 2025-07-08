# 🌍 Online Language School – Spring Boot REST API

This is a Spring Boot REST API application for managing an online language school. It supports operations for managing students, courses, teachers (employees), static content pages, and contact messages.

---

## ✅ Features

- Full CRUD support for:
    - Students
    - Courses
    - Employees (Teachers)
    - Contact messages
    - Static page content
- Relationship management:
    - Many-to-Many between Students and Courses
    - One-to-Many between Employee and Courses
- In-memory H2 database (no external DB required)
- JSON serialization with cycle handling (`@JsonManagedReference`, `@JsonBackReference`)

---

## 🧱 Entities Overview

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

### Students
| Method | Endpoint                        | Description                   |
|--------|----------------------------------|-------------------------------|
| GET    | `/api/students`                 | Get all students              |
| GET    | `/api/students/{id}`            | Get student by ID             |
| POST   | `/api/students`                 | Create new student            |
| PUT    | `/api/students/{id}`            | Update existing student       |
| DELETE | `/api/students/{id}`            | Delete student                |
| POST   | `/api/students/{id}/courses/{courseId}` | Enroll student in course |

### Courses
| Method | Endpoint              | Description              |
|--------|------------------------|--------------------------|
| GET    | `/api/courses`        | Get all courses          |
| GET    | `/api/courses/{id}`   | Get course by ID         |
| POST   | `/api/courses`        | Create new course        |
| PUT    | `/api/courses/{id}`   | Update course            |
| DELETE | `/api/courses/{id}`   | Delete course            |

### Employees (Teachers)
| Method | Endpoint                   | Description                   |
|--------|-----------------------------|-------------------------------|
| GET    | `/api/employees`          | Get all employees             |
| GET    | `/api/employees/{id}`     | Get employee by ID            |
| POST   | `/api/employees`          | Create new employee           |
| PUT    | `/api/employees/{id}`     | Update existing employee      |
| DELETE | `/api/employees/{id}`     | Delete employee               |

### Contact Messages
| Method | Endpoint               | Description                  |
|--------|--------------------------|------------------------------|
| GET    | `/api/contact`         | Get all contact messages     |
| GET    | `/api/contact/{id}`    | Get a specific message       |
| POST   | `/api/contact`         | Submit a new message         |
| DELETE | `/api/contact/{id}`    | Delete a message             |

### Static Pages
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

### Create Course
```json
{
  "language": "French",
  "level": "Beginner",
  "description": "Intro to French",
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
  "message": "I have a question about the French course."
}
```

---

## ⚙️ Configuration

The app connects to a remote MySQL database at 192.168.0.17:3306/my_db with user remoteadmin and password springcourse. Hibernate auto-updates the schema. Basic auth is enabled with user michael and password qwerty. Settings are in application.properties under the "macmini" profile.

---

## 📁 Project Structure (src/main/java)
```
com.zaurtregulov.spring.springboot.spring_data_rest
├── config/
├── controller/
├── dao/
├── entity/
└── SpringDataRestApplication.java
```

---

## 🔒 Security

All requests to /api/** are protected by basic HTTP authentication with username michael and password qwerty (from application.properties). Swagger UI and Actuator are publicly accessible. CSRF is disabled.

---

## 📃 License

Free to use for learning and educational purposes.
