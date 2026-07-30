# Ecommerce API 🛒

A RESTful backend API for an e-commerce platform built with Spring Boot.

This project provides secure and scalable APIs for managing users, products, categories, orders, and authentication using JWT.

---

## 🚀 Technologies

* Java 21
* Spring Boot 4
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Docker
* Swagger / OpenAPI
* JUnit 5
* Mockito

---

## 🏗️ Architecture

The application follows a layered architecture:

```
Controller Layer
        |
        v
Service Layer
        |
        v
Repository Layer
        |
        v
Database (MySQL)
```

The project is organized following clean code principles with separation of responsibilities between:

* Controllers: Handle HTTP requests and responses
* Services: Business logic
* Repositories: Database access
* DTOs: Data transfer between layers
* Entities: Database models
* Security: Authentication and authorization

---

## ✨ Features

## 🔐 Authentication & Security

* User registration
* User authentication
* JWT token generation
* JWT validation filter
* Role-based authorization
* Protected REST endpoints

---

## 👤 User Management

* Create users
* Update user information
* Manage user roles
* Secure user access

---

## 📦 Product Management

* Create products
* Update products
* Delete products
* Retrieve products
* Product search functionality

---

## 🗂️ Category Management

* Create categories
* Update categories
* Delete categories
* Associate products with categories

---

## 🛒 Order Management

* Create customer orders
* Manage order status
* Retrieve customer orders
* Handle order details

---

## 📂 Project Structure

```
src/main/java/com/example/ecommerce

├── config
│   ├── SecurityConfig
│   └── OpenApiConfig
│
├── controller
│   ├── AuthController
│   ├── ProductController
│   └── OrderController
│
├── dto
│
├── entity
│
├── exception
│
├── mapper
│
├── repository
│
├── security
│   ├── JwtFilter
│   └── JwtService
│
└── service
```

---

## ⚙️ Installation

### 1. Clone the repository

```bash
git clone https://github.com/youbirox/ecommerce-api.git
```

Navigate into the project:

```bash
cd ecommerce-api
```

---

## 🗄️ Database Configuration

Create a MySQL database:

```sql
CREATE DATABASE ecommerce;
```

Configure your database connection in:

```
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ▶️ Run the Application

Using Maven Wrapper:

Linux / Mac:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

Or with Maven:

```bash
mvn spring-boot:run
```

---

## 📖 API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

API documentation is generated using OpenAPI 3.

---

## 🧪 Running Tests

Execute tests with:

```bash
./mvnw test
```

or:

```bash
mvn test
```

Tests include:

* Controller tests
* Service tests
* Security tests

---

## 🐳 Docker Support

Build Docker image:

```bash
docker build -t ecommerce-api .
```

Run container:

```bash
docker run -p 8080:8080 ecommerce-api
```

---

## 🔄 Git Workflow

The project uses Git branches:

```
main
 |
 └── develop
      |
      └── feature/*
```

* `main`: Stable production version
* `develop`: Active development
* `feature/*`: New features

---

## 🚀 Future Improvements

* Docker Compose environment (Spring Boot + MySQL)
* Redis caching
* Payment integration
* CI/CD with GitHub Actions
* SonarQube code quality analysis
* AWS deployment
* Monitoring with Prometheus and Grafana
* Kubernetes deployment

---

## 👨‍💻 Author

**Ayoub Moutii**

Backend Developer | Spring Boot | DevOps | Cybersecurity

---

⭐ If you find this project useful, feel free to star the repository.
