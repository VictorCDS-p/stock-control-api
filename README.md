````markdown
# Stock Control API

A backend service for managing products, raw materials, and production simulations.  
Built with **Quarkus 3**, **Hibernate ORM Panache**, and **PostgreSQL**. Designed for inventory and production management scenarios.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Environment Setup](#environment-setup-env)
- [Database Setup](#database-setup)
- [Quarkus Configuration](#quarkus-configuration)
- [Running the Backend](#running-the-backend)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Postman Collection](#postman-collection)
- [Contributing](#contributing)
---

## Features

- CRUD operations for **Products** and **Raw Materials**.
- Add raw materials to a product.
- Simulate production runs.
- API documentation through **OpenAPI / Swagger UI**.
- Uses **environment variables** for secure database configuration.

---

## Tech Stack

- **Language:** Java 25
- **Framework:** Quarkus 3.32.1
- **ORM:** Hibernate ORM with Panache
- **Database:** PostgreSQL
- **Testing:** JUnit 5, Mockito, Rest-Assured
- **API Documentation:** OpenAPI 3.1, Swagger UI

---

## Environment Setup (.env)

Create a `.env` file in the root directory:

```env
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_postgres_password_here
POSTGRES_URL=jdbc:postgresql://localhost:5432/stockdb
````

> ⚠️ Replace `your_postgres_password_here` with your PostgreSQL user password.

---

## Database Setup

1. **Install PostgreSQL** if not installed.
2. **Create the database:**

```sql
CREATE DATABASE stockdb;
```

3. **Ensure the user exists and has privileges:**

```sql
CREATE USER postgres WITH PASSWORD 'your_postgres_password_here';
GRANT ALL PRIVILEGES ON DATABASE stockdb TO postgres;
```

> Replace `'your_postgres_password_here'` with your own password.

---

## Running the Backend

```bash
# Build and install dependencies
./mvnw clean install -U

# Start Quarkus in dev mode
./mvnw quarkus:dev
```

Access the API:

* **Backend Base URL:** `http://localhost:8080`
* **Swagger UI:** `http://localhost:8080/q/swagger-ui`
* **OpenAPI JSON:** `http://localhost:8080/q/openapi`

---

## API Endpoints

### Products

| Method | Endpoint                      | Description                   |
| ------ | ----------------------------- | ----------------------------- |
| GET    | `/products`                   | List all products             |
| POST   | `/products`                   | Create a new product          |
| GET    | `/products/{id}`              | Find a product by ID          |
| PUT    | `/products/{id}`              | Update a product              |
| DELETE | `/products/{id}`              | Delete a product              |
| POST   | `/products/{id}/add-material` | Add raw material to a product |

**Product Request DTO:**

```json
{
  "code": "string",
  "name": "string",
  "price": 0
}
```

**Product Material Request DTO:**

```json
{
  "rawMaterialId": 1,
  "requiredQuantity": 10
}
```

### Raw Materials

| Method | Endpoint              | Description               |
| ------ | --------------------- | ------------------------- |
| GET    | `/raw-materials`      | List all raw materials    |
| POST   | `/raw-materials`      | Create a new raw material |
| GET    | `/raw-materials/{id}` | Find a raw material by ID |
| PUT    | `/raw-materials/{id}` | Update a raw material     |
| DELETE | `/raw-materials/{id}` | Delete a raw material     |

**Raw Material Request DTO:**

```json
{
  "code": "string",
  "name": "string",
  "stockQuantity": 100
}
```

### Production

| Method | Endpoint                 | Description             |
| ------ | ------------------------ | ----------------------- |
| GET    | `/production/simulation` | Simulate all production |

---

## Testing

The project includes **unit tests**, **integration tests**, and uses an in-memory **H2 database** for testing.

### Run Tests

```bash
# Run unit and integration tests
./mvnw clean test

# Run only unit tests
./mvnw test -Dtest=*UnitTest

# Run only integration tests
./mvnw verify -Pintegration-tests
```

> ✅ All tests are automatically executed using JUnit 5 and Mockito for mocking.
> The H2 database is used by default to avoid affecting your local PostgreSQL instance.

---

## Postman Collection

A Postman collection is included for testing all API endpoints without manually creating requests:

* File: `stock-control-api/stock-control-api.postman_collection.json`
* Import into Postman to explore and test the API interactively.

---
## Repository

The backend source code is available at:

[https://github.com/VictorCDS-p/stock-control-api](https://github.com/VictorCDS-p/stock-frontend)

---

## Contributing

1. Fork the repository.
2. Create your feature branch: `git checkout -b feature-name`
3. Commit your changes: `git commit -m "Add feature"`
4. Push to the branch: `git push origin feature-name`
5. Create a pull request.

---
