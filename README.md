# Asol Orders Demo – Java Back-End

A modular Java/Spring Boot REST API for managing users and orders. Built as a showcase project to demonstrate clean architecture, modularization, and real-world back-end service design.

## Modules Overview

- **domain-model** – Shared DTOs and entities
- **order-service** – Order-related REST endpoints and logic
- **user-service** – Basic user model and authentication stub
- **mono** – Monolithic composition of the modules for local dev & testing

## Features Implemented

- Docker friendly multi-module Maven project setup
- REST endpoints for CRUD operations for:
  - Orders
  - Users
- Swagger/OpenAPI documentation
- JSON-based payloads
- Monolithic runnable JAR
- Preliminary Spring Security integration, support for auditing
- JWT authentication
- Basic datasets for users and orders

## Planned Features

- Full JWT lifecycle
- Role-base access controll
- User scoring feature
- Unit & integration test coverage
- Full containirezation and orchestration
- Fix dataset duping

## Running the Project

### In Intelij IDEA

- Use the .\.idea\runConfigurations\mono.xml launch config

### Using Maven

- Run mvn clean install
- Run mvn spring-boot:run -pl mono