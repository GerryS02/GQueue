# GQueue

An event-driven flash sale system built to handle high traffic, process orders efficiently, and prevent inventory overselling during peak demand.

GQueue uses **Spring Boot, Apache Kafka, and MySQL** to separate order requests from background processing, reducing database pressure during traffic spikes. With **MySQL Pessimistic Locking (`PESSIMISTIC_WRITE`)**, the system prevents race conditions and ensures accurate inventory management when multiple users attempt to purchase the same item concurrently.

## Tech Stack & Tools

 - **Core Framework:** Java 17, Spring Boot 3.3.x
 - **Database & ORM:** MySQL 8.0, Spring Data JPA, Hibernate
 - **Message Broker:** Apache Kafka, ZooKeeper
 - **Containerization:** Docker, Docker Compose
 - **Testing & Quality:** JUnit 5, Mockito, Spring Boot Test
 - **Utilities:** Lombok, Bean Validation, Maven

## System Architecture

![GQueue Architecture Diagram](https://i.postimg.cc/HkMQDJzT/Order-Processing-Architecture-Flowchart.png)

## Key Technical Highlights & Learnings

 - **Asynchronous Order Processing:** Used Apache Kafka to process orders in the background, keeping HTTP requests separate from inventory updates.
 - **Concurrency & Inventory Management:** Implemented JPA Pessimistic Locking (`PESSIMISTIC_WRITE`) with `@Transactional` to prevent race conditions and overselling during concurrent purchases.
 - **Exception Handling:** Used `@RestControllerAdvice` and custom exceptions like `ResourceNotFoundException` to return consistent REST API error responses, including `404 Not Found` and `400 Bad Request`.
 - **Dockerized Environment:** Used Docker Compose to run MySQL, Kafka, and ZooKeeper together for easier setup and development.

## How to Run Locally

### Prerequisites

 Make sure you have the following installed:

 - Java 17+
 - Docker Desktop
 - Git

### 1. Clone the Repository

 ```bash
 git clone https://github.com/GerryS02/GQueue.git
 cd GQueue
 ```

### 2. Start the Infrastructure

 Start MySQL, Kafka, and ZooKeeper using Docker Compose:

 ```bash
 docker compose up -d
 ```

### 3. Run the Application

 ```bash
 ./mvnw spring-boot:run
 ```

 The application will start at `http://localhost:8080`.

## API Reference

### Items

#### Create an Item

`POST /items`

Request body:

```json
{
  "name": "PlayStation 5",
  "stockQuantity": 50,
  "price": 499.99
}
```

Response: `201 Created`

#### Get All Items

`GET /items`

Response: `200 OK`

#### Get Item by ID

`GET /items/{id}`

Response: `200 OK`

### Orders

#### Place an Order

`POST /orders`

Request body:

```json
{
  "userId": 101,
  "itemId": 1
}
```

Response: `201 Created`

Example response:

```json
{
  "id": 1,
  "userId": 101,
  "itemId": 1,
  "status": "PENDING"
}
```

The order is initially marked as `PENDING` while Kafka processes it in the background. Its final status will be `CONFIRMED` or `FAILED`, depending on inventory availability.