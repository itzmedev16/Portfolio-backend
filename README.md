# Portfolio Backend API

A complete, production-ready REST API backend for a Personal Portfolio Management System built using **Spring Boot**, **Spring Security**, and **MySQL**.

This system supports a **Public Portfolio Website** (accessible without authentication) and a **Hidden Admin Dashboard** (secured via JWT).

---

## Technology Stack

* **Language**: Java 17 / 21
* **Framework**: Spring Boot 3.3.1
* **Security**: Spring Security & JWT (JSON Web Tokens)
* **ORM**: Hibernate & Spring Data JPA
* **Database**: MySQL 8.x
* **Build Tool**: Maven
* **Libraries**: Lombok, Jakarta Validation, Spring Mail

---

## Directory Structure

The project follows a clean layered architecture:

```
src/main/java/com/portfolio/
├── PortfolioBackendApplication.java   # Spring Boot Main Class
├── config/                            # Custom Configuration files (WebConfig, DataInitializer)
├── controller/                        # REST Controller layer (Auth, Public, Admin)
├── dto/                               # Data Transfer Objects (Requests & Responses)
├── entity/                            # JPA Database Entities
├── exception/                         # Custom Exception classes & Global Exception Handler
├── repository/                        # Spring Data JPA Repository layer
├── security/                          # Security filters, providers, and settings
└── service/                           # Business logic service layer
```

---

## Authentication & Default Admin Account

The system is configured with **exactly one admin account** to protect portfolio contents. There is **no registration endpoint**.

* **Login Endpoint**: `POST /api/auth/login`
* **Request Format**: JSON payload with `email` and `password`.
* **Configured Admin Credentials**:
  * Configurable in `application.properties` via `app.admin.email` and `app.admin.password` (defaults to `admin@portfolio.local` and `admin123` respectively).
* **Behavior**: During application startup, a `CommandLineRunner` checks if the admin user exists. If not, it automatically creates the account with a BCrypt encrypted password.
* **Security Rules**:
  * All public portfolio routes are open without token requirements.
  * All `/api/admin/**` routes require a valid JWT token sent in the headers as:
    `Authorization: Bearer <your_jwt_token>`

---

## Database Configuration & Run Instructions

### 1. Prerequisites
* Java JDK 17 (or newer)
* Maven 3.x
* MySQL Service running locally or remotely

### 2. Configure Database and Mail settings
Edit [application.properties](src/main/resources/application.properties):
* Update `spring.datasource.username` and `spring.datasource.password` to match your local MySQL configuration.
* Update `spring.mail.username` and `spring.mail.password` to enable contact form email notifications (uses Gmail SMTP by default). If SMTP credentials are not configured, the application logs a warning, but contact submissions will still succeed.

### 3. Database Schema setup
The application uses Hibernate `ddl-auto=update` to dynamically generate tables upon startup.
Alternatively, you can manually run the raw SQL schema provided in:
* [schema.sql](schema.sql) (Table structures)
* [data.sql` (Sample seed data for portfolio items)

### 4. Build and Run the Application
In your terminal, navigate to the project directory and run:

```bash
# Build the project
mvn clean compile

# Run tests
mvn test

# Run the spring-boot application
mvn spring-boot:run
```

The server will start on port `8080` (accessible at `http://localhost:8080`).

---

## File Uploads

Uploads are handled securely. Supported folders will automatically be created in the root directory under `uploads/`. Files are served statically via `/uploads/**`.

* **Image uploads**: Supported types: `JPEG`, `PNG`, `GIF`, `WEBP`. Max size: `10MB`.
* **PDF / Resume upload**: Supported type: `application/pdf`. Max size: `10MB`.
* **Utility Upload API**: `POST /api/admin/upload` (Form-data: `file` file). Returns a JSON response containing:
  ```json
  { "fileUrl": "/uploads/unique-uuid.png" }
  ```

---

## API Endpoints Reference

### Public APIs (No Authentication Required)

| Endpoint | Method | Description |
|---|---|---|
| `/api/profile` | GET | Retrieve portfolio owner details |
| `/api/skills` | GET | Retrieve enabled skills sorted by display order |
| `/api/projects` | GET | Retrieve projects sorted by display order |
| `/api/certificates` | GET | Retrieve certificates sorted by display order |
| `/api/resume` | GET | Retrieve details and link of the active PDF resume |
| `/api/contact` | POST | Submit contact form (saves to DB and triggers email notification) |

### Admin APIs (JWT Required)

| Component | Endpoint | Method | Description |
|---|---|---|---|
| **Auth** | `/api/auth/login` | POST | Log in and return JWT token (No JWT required) |
| **Profile** | `/api/admin/profile` | GET | Retrieve profile details |
| | `/api/admin/profile` | PUT | Update profile details |
| **Skills** | `/api/admin/skills` | GET | List all skills (including disabled) |
| | `/api/admin/skills` | POST | Create a new skill |
| | `/api/admin/skills/{id}` | PUT | Update an existing skill |
| | `/api/admin/skills/{id}` | DELETE | Delete a skill |
| **Projects** | `/api/admin/projects` | GET | List all projects |
| | `/api/admin/projects` | POST | Create a new project |
| | `/api/admin/projects/{id}` | PUT | Update an existing project |
| | `/api/admin/projects/{id}` | DELETE | Delete a project |
| **Certificates** | `/api/admin/certificates` | GET | List all certificates |
| | `/api/admin/certificates` | POST | Create a new certificate |
| | `/api/admin/certificates/{id}` | PUT | Update an existing certificate |
| | `/api/admin/certificates/{id}` | DELETE | Delete a certificate |
| **Resume** | `/api/admin/resume` | GET | Get active resume metadata |
| | `/api/admin/resume` | POST | Upload active resume PDF (Form-data: `file`) |
| | `/api/admin/resume` | DELETE | Delete active resume (deletes database record and file) |
| **Messages** | `/api/admin/messages` | GET | List all contact messages |
| | `/api/admin/messages/{id}/read` | PUT | Mark message as read |
| | `/api/admin/messages/{id}` | DELETE | Delete message |
| **Dashboard** | `/api/admin/dashboard` | GET | Get dashboard stats (Total Skills, Projects, Certificates, Unread Messages) |
| **Utility** | `/api/admin/upload` | POST | Upload general media file (Form-data: `file`) |

---

## Postman Collection

Import the included file [PortfolioBackendAPI.postman_collection.json](PortfolioBackendAPI.postman_collection.json) to quickly test the APIs.

1. Trigger the **Admin Login** request inside the **Authentication** folder to retrieve a token.
2. The login script automatically saves the token to the `jwtToken` collection variable.
3. All admin calls are pre-configured to use the token. You can call them immediately after logging in.
