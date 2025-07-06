ShauryaSoftrack Task
Author: Jalp Panchal
Date: 05-07-2025 Saturday

This is a Spring Boot application that provides a REST API endpoint for user registration. The endpoint /api/register accepts a user's name and mobile number, validates the input, saves it to a MySQL database, and returns a success response with the user ID.
Project Structure

Package: com.jalp
Database: MySQL, table users (auto-created)
Port: 9191
Main Files:
UserEntity.java: JPA entity for the users table.
UserDTO.java: DTO for request validation.
UserRepository.java: JPA repository for database operations.
UserController.java: REST controller for the /api/register endpoint.
application.properties: Configuration for MySQL and Hibernate.
pom.xml: Maven dependencies and build configuration.



Prerequisites

Java 17
Maven
MySQL (running on localhost:3306 with username root and password root)
Postman (for testing)

Setup Instructions

Clone the Repository:git clone <repository-url>
cd ShauryaSoftrack_Task


Configure MySQL:
Ensure MySQL is running.
Create a database named ShauryaSoftrack_Task (or ensure it exists).
Verify the database credentials in application.properties:spring.datasource.username=root
spring.datasource.password=root
spring.datasource.url=jdbc:mysql://localhost:3306/ShauryaSoftrack_Task
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database=MYSQL




Build and Run:mvn clean install
mvn spring-boot:run

The application will start on http://localhost:9191.

API Endpoint

URL: http://localhost:9191/api/register
Method: POST
Headers: Content-Type: application/json
Request Body:{
    "name": "John Doe",
    "mobileNumber": "+1234567890"
}


Validations:
name: Required, 2-50 characters, only letters and spaces (no numbers or special characters).
mobileNumber: Required, 10-13 digits (excluding optional +), must start with an optional + followed by digits.
mobileNumber must be unique in the database.


Example of Success Response (201 Created):{
    "id": "684396be-861b-4e05-a87a-480d77fa0eb2",
    "message": "User registered successfully"
}


Error Responses (400 Bad Request):
Invalid input:{
    "name": "Name cannot contain numbers or special characters",
    "mobileNumber": "Mobile number must be 10 to 13 digits"
}


Duplicate mobile number:{
    "error": "Mobile number already registered"
}





Testing with Postman

Open Postman.
Create a new POST request to http://localhost:9191/api/register.
Set the header Content-Type: application/json.
Add a JSON body, e.g.:{
    "name": "Jalp Panchal",
    "mobileNumber": "9104441284"
}


Send the request and verify the response.
Test invalid cases, e.g.:
Name with numbers: {"name": "Jalp123", "mobileNumber": "+1234567890"}
Invalid mobile number: {"name": "Jalp Panchal", "mobileNumber": "+123456789"}


Dependencies

Spring Boot 3.5.3
Spring Data JPA
Spring Web
Spring Validation
MySQL Connector
Lombok
Maven

Notes

The users table is auto-created by Hibernate (spring.jpa.hibernate.ddl-auto=update).
Ensure MySQL is configured with the correct credentials.
The application uses Lombok to reduce boilerplate code (ensure your IDE supports Lombok).
For production, consider changing spring.jpa.hibernate.ddl-auto to validate or none.
