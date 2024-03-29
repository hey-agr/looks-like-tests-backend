# Looks like tests backend API
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/831a7ee714604f58b2af70c2609e78fd)](https://app.codacy.com/gh/hey-agr/looks-like-tests-backend?utm_source=github.com&utm_medium=referral&utm_content=hey-agr/looks-like-tests-backend&utm_campaign=Badge_Grade_Settings)

## Simple Spring Boot Application

### Roles: Student, Teacher, Admin

#### 1. Admin creates tests
#### 2. Admin assigns teachers to students
#### 3. Teacher assigns tests to students
#### 4. Student passes tests
#### 5. Teacher verifies text answers

### Test questions types: Option, Option multiply, Writing

#### 1. Option - choose right option
#### 2. Option multiply - choose 2 or more right options
#### 3. Writing - text answer (should be verified by teacher)

## How to run application?
docker-compose up -d

## Swagger
http://localhost:8080/swagger-ui/index.html

## PgAdmin
http://localhost:5050/

Username: user@pgadmin.org

Password: admin

## Spring Native / Spring Boot 3 (Data JPA, Web, Security) / PostgresSQL / Flyway / Docker / Swagger / Lombok / Mapstruct