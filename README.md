# Bank Employee Management System

## Overview
The **Bank Employee Management System** is a full-stack web application that enables **admins** to manage employees efficiently and allows **employees** to update their personal information. This system provides secure authentication, role-based access control, and a streamlined interface for handling employee records.

## Tech Stack
### Frontend
- **Angular** (with Angular Material for UI components)
- **TypeScript**
- **HTML, CSS, SCSS**

### Backend
- **Spring Boot** (REST API)
- **Spring Security** (JWT Authentication)
- **MySQL** (Relational Database)
- **Hibernate/JPA** (ORM for database interaction)

## Features
### Admin Features
- Employee onboarding with **auto-generated Employee ID & Password**
- View all employees with **filtering by branch**
- View **specific employee details**
- **Approve, update, or delete** employee records
- Secure authentication and role-based access

### Employee Features
- Secure **login & authentication**
- View and update **personal profile** (Phone Number, Address, Date of Birth, etc.)
- **Change password** securely

## Installation and Setup
### Prerequisites
- **Node.js** & **Angular CLI** (for frontend)
- **Java 17+** & **Spring Boot** (for backend)
- **MySQL** (for database)

### Clone the Repository
```sh
git clone https://github.com/your-repo/bank-employee-management.git
cd bank-employee-management
```

### Backend Setup
1. Navigate to the backend folder:
   ```sh
   cd backend
   ```
2. Configure database credentials in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/bank_employee_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```
3. Run the backend:
   ```sh
   mvn spring-boot:run
   ```

### Frontend Setup
1. Navigate to the frontend folder:
   ```sh
   cd frontend
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Start the frontend server:
   ```sh
   ng serve --open
   ```

## API Endpoints
### Authentication
- **POST** `/auth/login` - Login and receive a JWT token
- **POST** `/auth/signup` - Register a new employee/admin

### Admin APIs
- **GET** `/api/v1/employees` - Get all employees
- **GET** `/api/v1/employees/{id}` - Get employee by ID
- **POST** `/api/v1/employees` - Add a new employee
- **PUT** `/api/v1/employees/{id}` - Update employee details
- **DELETE** `/api/v1/employees/{id}` - Delete an employee

### Employee APIs
- **GET** `/api/v1/current-employee` - Get logged-in employee details
- **PUT** `/api/v1/update-details` - Update personal profile
- **PATCH** `/api/v1/update-password` - Change password

## Security
- **JWT-based authentication** for secure API access
- **Role-based authorization** (Admin & Employee)
- **Password hashing** using **BCrypt**

## Future Enhancements
- Add **email notifications** for employee onboarding
- Implement **two-factor authentication (2FA)**
- Add **leave management & payroll module**

## Contributors
- **Deepanjan** (Developer)

## License
This project is licensed under the **MIT License**.

---
This README provides a clear project overview. Let me know if you need any modifications! 🚀