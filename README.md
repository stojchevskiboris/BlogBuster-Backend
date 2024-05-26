# BlogBuster Backend

## Overview
BlogBuster is an online platform where users can engage in discussions and share content across a wide range of topics. The BlogBuster Backend is built with Java Spring Boot, simplifying web application development and making it production-ready quickly. It follows a multi-layered architecture:

- **Controller Layer**: Handles HTTP requests.
- **Service Layer**: Implements business logic.
- **Repository Layer**: Interacts with the database.
- **Model Layer**: Defines data models, DTOs, and mappers.

## Key Features

### Authentication & Authorization

- **JwtAuthenticationFilter.java**: Intercepts HTTP requests, authenticates via JWT.
- **SecurityConfiguration.java**: Configures security settings, including CORS.

### Data Handling

- **Entities**: Represent business objects (e.g., `User.java`, `Post.java`).
- **DTOs**: Transfer data between frontend and backend.
- **Mappers**: Convert between entities and DTOs.

### CRUD Operations

- **Repositories**: Define CRUD methods (e.g., `PostRepository.java`, `UserRepository.java`).

### Business Logic

- **Services**: Define business operations (e.g., `PostService.java`, `UserService.java`).

### REST Controllers

- Handle HTTP requests for various operations (e.g., `PostRestController.java`, `UserController.java`).

## Getting Started

### Prerequisites

- JDK
- Maven
- Spring Boot

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/stojchevskiboris/BlogBuster-Backend.git
   cd BlogBuster-Backend
2. Build and run the project:
   ```bash
   mvn clean install
   mvn spring-boot:run
