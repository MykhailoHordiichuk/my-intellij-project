
# 🏫 EasyEng – Online Language School API (EN)

**EasyEng** — is a Spring Boot REST API for an online language school. Allows managing users, courses, teachers, content pages, and incoming contact messages. The service is deployed on Render.

> 🔗 **Live Backend**: https://easyeng-ccwf.onrender.com  
> 🌐 **Frontend (in development)**: https://easyeng.netlify.app  
> 🎨 **Figma (design)**: [Design](https://www.figma.com/design/WwmXdOPnFgiTmbedoUQu9M/Untitled?node-id=0-1&m=dev&t=CSekH0d9fIuvgupt-1)  
> 🧪 **Swagger UI**: https://easyeng-ccwf.onrender.com/swagger-ui/index.html  

---

## ✅ Features

- 🔐 User registration and login
- 📘 CRUD for courses and teachers
- 💬 Sending messages via contact form
- 🧾 Managing static content (pages)
- ⚙️ Swagger UI and Actuator endpoints
- 🔐 Basic Auth + CORS
- 🔄 Passwords are hashed using BCrypt
- 📦 Ready for deployment (Render Docker build)

---

## 🛡️ Security

- `/api/users/**` — registration and login (open access)
- `/api/**` — require Basic Auth (`michael:qwerty`)
- `/swagger-ui/**` и `/v3/api-docs/**` — open access
- CSRF is disabled (for REST)

---

## 📁 API Structure

### 👤 Users (`/api/users`)
| Method | Endpoint           | Description                      |
|--------|--------------------|-------------------------------|
| POST   | `/register`        | Register user      |
| POST   | `/login`           | Login (password check)        |
| GET    | `/`                | Get all users   |
| GET    | `/{id}`            | Get by ID                |
| PUT    | `/{id}`            | Update user         |
| DELETE | `/{id}`            | Delete user          |

> DTO: `UserRegisterDTO`, `UserLoginDTO`

---

### 📘 Courses (`/api/courses`)
| Method | Endpoint           | Description                      |
|--------|--------------------|-------------------------------|
| GET    | `/`                | All courses                     |
| GET    | `/{id}`            | Get course by ID           |
| POST   | `/`                | Add new course           |
| PUT    | `/{id}`            | Update курс                 |
| DELETE | `/{id}`            | Delete курс                  |

---

### 👩‍🏫 Teachers (`/api/employees`)
| Method | Endpoint           | Description                      |
|--------|--------------------|-------------------------------|
| GET    | `/`                | All teachers             |
| GET    | `/{id}`            | Teacher by ID           |
| POST   | `/`                | Add teacher        |
| PUT    | `/{id}`            | Update                      |
| DELETE | `/{id}`            | Delete                       |

---

### 💬 Contact Messages (`/api/contact`)
| Method | Endpoint           | Description                      |
|--------|--------------------|-------------------------------|
| GET    | `/`                | Get all messages        |
| GET    | `/{id}`            | Get by ID                |
| POST   | `/`                | Send message           |
| DELETE | `/{id}`            | Delete                       |

---

### 📄 Static Pages (`/api/pages`)
| Method | Endpoint           | Description                      |
|--------|--------------------|-------------------------------|
| GET    | `/`                | All pages                  |
| GET    | `/{id}`            | Get by ID                |
| POST   | `/`                | Create page              |
| DELETE | `/{id}`            | Delete страницу              |

---

## 🧪 Example JSON Payloads

### 🔐 Register user
```json
{
  "email": "user@example.com",
  "password": "123456",
  "firstName": "John",
  "lastName": "Doe",
  "age": 25,
  "phoneNumber": "+123456789"
}
```

### 🔐 Login
```json
{
  "email": "user@example.com",
  "password": "123456"
}
```

### ➕ New course
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

### ➕ New message
```json
{
  "name": "Visitor",
  "email": "visitor@example.com",
  "message": "Hello! I want to know more about your courses."
}
```

---

## ⚙️ Technologies

- Java 17 + Spring Boot
- Spring Data JPA + PostgreSQL
- Spring Security (Basic Auth)
- Swagger (springdoc-openapi)
- Docker (multi-stage build)
- Deploy on [Render](https://render.com)
- CORS: `127.0.0.1:5500`, `easyeng.netlify.app`

---

## 🧾 Local Run

```bash
git clone https://github.com/MykhailoHordiichuk/my-intellij-project.git
cd my-intellij-project

# Run with Maven
mvn spring-boot:run
```

> The application will be available at: `http://localhost:8080`

---

## 🔑 Test Authorization

```bash
Username: michael
Password: qwerty
```

InMemory user is used for admin.

---

## 🔍 Swagger

Available at:
```
/swagger-ui/index.html
```

---

## 🧩 TODO / TODO / Ideas

- [ ] Replace Basic Auth with JWT
- [ ] Implement roles: ADMIN / USER / TEACHER
- [ ] Add validation for DTOs and responses
- [ ] Integrate email notifications
- [ ] Write unit tests for controllers

---

## 📃 License

Free to use for educational and non-commercial purposes.

---

## 👨‍💻 Authors

- Mykhailo Hordiichuk — Backend Developer (https://github.com/MykhailoHordiichuk/my-intellij-project.git)   
- Hordiichuk Anna — UI/UX Design  
- Karaman Kostiantyn — Frontend Developer