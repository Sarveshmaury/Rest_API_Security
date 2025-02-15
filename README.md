Spring Boot CRUD Demo - Security Configuration
This project demonstrates how to configure security in a Spring Boot application using JDBC authentication with custom user roles. The security configuration is set up for controlling access to API endpoints based on user roles.

Overview
The DemoSecurityConfig class contains the security configuration for the application. It configures Spring Security to authenticate users from a database using JdbcUserDetailsManager. It also defines role-based access control (RBAC) to secure API endpoints for different user roles (e.g., EMPLOYEE, MANAGER, ADMIN).

Key Features:
Uses JDBC authentication for user and role management.
Defines role-based access control (RBAC) for various HTTP methods (GET, POST, PUT, DELETE).
Disables CSRF protection (appropriate for stateless APIs).
HTTP Basic authentication is used for API security.
Components
1. userDetailsManager() Bean
Configures JdbcUserDetailsManager to interact with the database for loading user details and roles.
SQL queries are used to fetch user data and authorities from the database:
select user_id, pw, active from members where user_id=? for user credentials.
select user_id, role from roles where user_id=? for user roles.
2. filterChain() Bean
Configures the HTTP security to restrict access to the /api/employees endpoint based on user roles:
GET requests to /api/employees are allowed only for users with the EMPLOYEE role.
POST and PUT requests to /api/employees are allowed only for users with the MANAGER role.
DELETE requests to /api/employees/** are allowed only for users with the ADMIN role.
HTTP Basic Authentication is enabled.
CSRF protection is disabled (common for stateless APIs).
3. (Commented Out) In-Memory User Details Configuration
This part, currently commented out, demonstrates how to configure in-memory users using InMemoryUserDetailsManager.
It defines users with various roles (EMPLOYEE, MANAGER, ADMIN) for development or testing purposes.
Configuration
JDBC Configuration:

Users are fetched from the members table.
Roles are fetched from the roles table.
Role-Based Access Control:

HTTP methods (GET, POST, PUT, DELETE) on /api/employees are restricted to users based on their roles.
Authentication:

Basic HTTP authentication is used to secure the application.
Dependencies
This class assumes the following dependencies are included in your project:

spring-boot-starter-security
spring-boot-starter-jdbc
spring-boot-starter-web
spring-boot-starter-data-jpa
How to Use
Set up your Database:

Ensure your members and roles tables exist in the database, and the schema matches the SQL queries in JdbcUserDetailsManager.
Example schema:
sql
Copy
CREATE TABLE members (
    user_id VARCHAR(50) PRIMARY KEY,
    pw VARCHAR(255),
    active BOOLEAN
);

CREATE TABLE roles (
    user_id VARCHAR(50),
    role VARCHAR(50)
);
Run the Application:

The Spring Boot application can be started as usual using mvn spring-boot:run or by running the main application class.
API Security:

Basic authentication will prompt you for a username and password.
The access to the /api/employees endpoints will be based on the user's roles:
GET requires the EMPLOYEE role.
POST/PUT require the MANAGER role.
DELETE requires the ADMIN role.
Additional Notes
Disabling CSRF: CSRF is disabled for the API since it's designed to be stateless and doesn't rely on sessions.
Database: Ensure that the database contains the necessary user and role data, and adjust the queries if your schema differs.
Example Usage
Example SQL Data
sql
Copy
-- Sample member data
INSERT INTO members (user_id, pw, active) VALUES ('john', 'password123', TRUE);
INSERT INTO members (user_id, pw, active) VALUES ('jane', 'password123', TRUE);

-- Sample roles data
INSERT INTO roles (user_id, role) VALUES ('john', 'EMPLOYEE');
INSERT INTO roles (user_id, role) VALUES ('jane', 'MANAGER');
