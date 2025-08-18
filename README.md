# 🏫 EasyEng – Online Language School API

**EasyEng** is a Spring Boot REST API for an online language school.  
It manages users, courses, teachers, static pages, and contact messages.  
The service is deployed on **Render** with a PostgreSQL database.  

> 🔗 **Live Backend**: https://easyeng-ccwf.onrender.com  
> 🧪 **Swagger UI**: https://easyeng-ccwf.onrender.com/swagger-ui/index.html  

---

## ✅ Features

- 🔐 User registration and login  
- 📘 CRUD for courses and teachers  
- 💬 Contact form messages (send + admin view)  
- 📄 Manage static page content  
- ⚙️ Swagger UI + Actuator monitoring  
- 🔄 Password hashing with BCrypt  
- 🧾 DTO layer with validation  
- 📝 Centralized business logic in Service Layer  
- 📦 Ready for deployment (Render + PostgreSQL)  

---

## 🛡️ Security

- `/api/auth/**` — registration and login (open)  
- `/api/**` — secured with Basic Auth (`michael:qwerty`)  
- `/swagger-ui/**` and `/v3/api-docs/**` — open access  
- CSRF disabled (REST-friendly)  

---

## 📁 API Endpoints

### 👤 Users (`/api/users`)
- `POST /register` — Register user  
- `POST /login` — Login  
- `GET /` — List all users  
- `GET /{id}` — Get user by ID  
- `PUT /{id}` — Update user  
- `DELETE /{id}` — Delete user  

### 📘 Courses (`/api/courses`)
- `GET /` — All courses  
- `GET /{id}` — Course by ID  
- `POST /` — Add new course  
- `PUT /{id}` — Update course  
- `DELETE /{id}` — Delete course  

### 👩‍🏫 Teachers (`/api/employees`)
- `GET /` — All teachers  
- `GET /{id}` — Teacher by ID  
- `POST /` — Add teacher  
- `PUT /{id}` — Update teacher  
- `DELETE /{id}` — Delete teacher  

### 💬 Contact Messages (`/api/contact`)
- `GET /` — All messages  
- `GET /{id}` — Message by ID  
- `POST /` — Send new message  
- `DELETE /{id}` — Delete message  

### 📄 Pages (`/api/pages`)
- `GET /` — All pages  
- `GET /{id}` — Page by ID  
- `POST /` — Create new page  
- `PUT /{id}` — Update page  
- `DELETE /{id}` — Delete page  

---

## 🧪 Example Payloads

**Register User**
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

**Login**
```json
{
  "email": "user@example.com",
  "password": "123456"
}
```

**New Course**
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

**New Contact Message**
```json
{
  "name": "Visitor",
  "email": "visitor@example.com",
  "message": "Hello! I want to know more about your courses."
}
```

---

## ⚙️ Tech Stack

- Java 17 + Spring Boot  
- Spring Data JPA + PostgreSQL  
- Spring Security (Basic Auth)  
- Lombok (DTOs + Entities)  
- Hibernate Validator (validation)  
- Swagger (springdoc-openapi)  
- Docker (multi-stage build)  
- Deployment: Render (Backend + Postgres)  

---

## 🧾 Run Locally

```bash
git clone https://github.com/MykhailoHordiichuk/my-intellij-project.git
cd my-intellij-project
mvn spring-boot:run
```

App runs at `http://localhost:8080`  

Swagger UI: `http://localhost:8080/swagger-ui/index.html`  

---

## 🔑 Default Auth (for testing)

```txt
Username: michael
Password: qwerty
```

---

## 👨‍💻 Author

- **Mykhailo Hordiichuk** — Backend Developer  
  [GitHub](https://github.com/MykhailoHordiichuk/my-intellij-project)  
