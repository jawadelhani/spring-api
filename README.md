## Store API (Spring Boot)

A simple but production-ready **e‑commerce backend API** built with Spring Boot and MySQL. It exposes REST endpoints for managing products, users, carts, and basic store operations.

### Features
- **Product catalog**: List products, filter by category, view product details, create/update/delete products.
- **Shopping cart**: Create carts, add items, update quantities, remove items, and clear carts.
- **Users & profiles**: Manage users with addresses, profiles, and wishlists (favorite products).
- **Validation & error handling**: Request validation and consistent error responses via a global exception handler.
- **Database migrations**: Schema managed with Flyway for repeatable, version-controlled database changes.

### Tech Stack
- **Backend**: Java 23, Spring Boot 3.4 (Web, Data JPA, Validation)
- **Database**: MySQL
- **Migrations**: Flyway
- **Utilities**: Lombok, MapStruct

### Run Locally
1. Create a MySQL database named `store_api` and update credentials if needed (`pom.xml` Flyway plugin and `application.yaml`).
2. Build and run:
   - On Windows: `mvnw.cmd spring-boot:run`
   - On Linux/macOS: `./mvnw spring-boot:run`

