# Revoverflow-backend
RevOverflow Java Spring Backend

# Project Description
RevOverflow is a site dedicated to providing support to technical problems encountered by the Revature community during training and project work. This platform is intended to provide a positive, constructive location to seek help from the Revature community regarding challenges that developers frequently face. It also helps facilitate transfer of knowledge between associates and batches, helps document and solve common problems, and provides a mechanism for associates to gain knowledge by helping one another.

# Dependencies Needed
- Java 8
- Google Guava 20.0
- H2 1.4.200
- JWT 0.10.6
- Log4J 1.2.17
- SpringFox Swagger 2.9.2
- Spring Boot Starter Parent 2.2.1.RELEASE
  - Spring Boot DevTools
  - Spring Boot Starter
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Web
  - Spring Boot Starter Security
  - Spring Boot Starter Test
  - Hibernate Core 5.4.8.Final
  - PostgreSQL 42.2.8

# Features
- Secure route guarding with JWT to ensure navigation to a requested route is permitted.
- Employment of a RESTful API to handle HTTP requests with GET, PUT, POST, and DELETE.
- Utilizes JpaRepository within SpringData for streamlined data access with an H2 database.
- Implements a strategy of separation of concerns to decouple layers (i.e. Repo, Service, Model).

# Getting Started
- git clone <https://github.com/875-Trevin-Chester/revoverflow-backend.git>
  - The above git command will bring the repository into your specified directory.

# Usage
- Download Java version 8 to some location on the local machine.
- Import the project files into an IDE (STS used in development).
- Navigate to the 'Boot Dashboard' window and run the 'revoverflow-backend [devtools]' Spring Project.
