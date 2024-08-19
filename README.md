# Online Cake Shopping Web Application

## Overview

This project is a web application built with a React frontend and a Java/Spring Boot backend. It implements CRUD operations for four domain models and includes role-based authentication to manage access to different features based on user roles.

## Features

- **CRUD Operations:** Full Create, Read, Update, Delete operations for four domain models: Cakes, Flavors, Categories, and Users.
- **Role-Based Authentication:** Secure different parts of the application based on user roles (e.g., Admin, User).
- **Responsive Design:** The frontend is built using React and is fully responsive.
- **Backend:** Implemented using Java and Spring Boot.

## Prerequisites

- **Backend:**
  - Java 17
  - Spring Boot 3.2.0
  - Maven 3.9.5
  - MySQL
- **Frontend:**
  - Node.js 21.4.0
  - npm 10.2.4
  - React 18.3.1

## Setup Instructions

### Backend

1. Clone the repository:

   ```bash
   git clone https://github.com/kosseinecira/online-cake-shopping-web-app.git
   cd online-cake-shopping-web-app
   ```

2. Configure the database:

   - Update `application.properties` with your database configuration.

3. Build the project:

   ```bash
   ./mvnw clean install
   ```

4. Run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

5. The backend API will be accessible at `http://localhost:8080`.

### Frontend

1. Navigate to the frontend directory:

   ```bash
   cd ../front-end
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the React application:

   ```bash
   npm start
   ```

4. The frontend will be accessible at `http://localhost:3000`.

## API Endpoints

### Cake Domain Model

- **GET** `/cakes`: Retrieve all cakes.
- **GET** `/cakes/{cakeId}`: Retrieve a specific cake by ID.
- **POST** `/categories/{categoryId}/flavors/{flavorId}/cakes`: Create a new cake under a specific category and flavor.
- **PUT** `/categories/{categoryId}/flavors/{flavorId}/cakes/{cakeId}`: Update an existing cake.
- **DELETE** `/flavors/{flavorId}/cakes/{cakeId}`: Delete a specific cake by ID.
- **GET** `/flavors/{flavorId}/cakes`: Retrieve all cakes under a specific flavor.
- **GET** `/categories/{categoryId}/cakes`: Retrieve all cakes under a specific category.
- **GET** `/categories/{categoryId}/flavors/{flavorId}/cakes`: Retrieve all cakes under a specific category and flavor.

### Flavor Domain Model

- **GET** `/flavors`: Retrieve all flavors.
- **POST** `/flavors`: Create a new flavor.
- **GET** `/flavors/{id}`: Retrieve a specific flavor by ID.
- **PUT** `/flavors/{id}`: Update an existing flavor by ID.
- **DELETE** `/flavors/{id}`: Delete a specific flavor by ID.

### Category Domain Model

- **GET** `/categories`: Retrieve all categories.
- **POST** `/categories`: Create a new category.
- **GET** `/categories/{categoryId}`: Retrieve a specific category by ID.
- **PUT** `/categories/{categoryId}`: Update an existing category by ID.
- **DELETE** `/categories/{categoryId}`: Delete a specific category by ID.
- **GET** `/images/categories_images/{imageName}`: Retrieve an image associated with a category.

### User Domain Model

- **POST** `/users`: Create a new user.
- **GET** `/users`: Retrieve all users.
- **GET** `/users/{id}`: Retrieve a specific user by ID.
- **GET** `/users`: Retrieve a user by username, email, or phone (query parameters: `username`, `email`, `phone`).
- **PUT** `/users/{id}`: Update an existing user by ID.
- **PATCH** `/users/{id}`: Block or unblock a specific user by ID.
- **DELETE** `/users/{id}`: Delete a specific user by ID.

## Role-Based Authentication

The application includes role-based authentication:

- **Admin**: Full access to all CRUD operations across all domain models.
- **User**: Limited access depending on permissions.

## Monitoring and Tracing

This application uses the following tools for monitoring and tracing:

### Spring Boot Actuator

Spring Boot Actuator provides production-ready features to help you monitor and manage your application. It includes endpoints for health checks, metrics, and more.

- **Actuator Endpoints**: `/api/v1/actuator/health`, `/api/v1/actuator/info`, `/api/v1/actuator/prometheus`

### Prometheus

Prometheus is used to collect and store metrics from the application.

- **Prometheus Metrics Endpoint**: `/api/v1/actuator/prometheus`
- **Prometheus Server**: [http://localhost:9090](http://localhost:9090)

### Grafana

Grafana is used to visualize the metrics collected by Prometheus.

- **Grafana Dashboard**: [http://localhost:3000](http://localhost:3000)
- **Add Prometheus as a Data Source**: Configure the Prometheus URL in Grafana as `http://localhost:9090`.

### Zipkin

Zipkin is used for distributed tracing, helping to track requests across microservices.

- **Zipkin Server**: [http://localhost:9411](http://localhost:9411)
- **Configuration**: Configure the Zipkin base URL in `application.yml`.

### Running All Services Using Docker

To run all of them, use Docker:

```bash
docker compose up
```

## Testing

### Backend Tests:

Run the tests using Maven:

```bash
./mvnw test
```
