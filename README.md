# ValidateOAuthToken

## Project Overview

This project is a Spring Boot application that serves as an OAuth2 resource server. Its primary purpose is to validate OAuth2 access tokens and protect secured endpoints. The application is designed to be a robust and secure backend system, integrating various services for a comprehensive solution.

## Core Functionalities

- **OAuth2 Token Validation**: The application validates OAuth2 access tokens, ensuring that only authorized clients can access protected resources.
- **Payment Processing**: It integrates with **Razorpay** for seamless and secure payment processing.
- **Reporting**: The application uses **JasperReports** to generate and manage reports.
- **Data Persistence**: It uses **Spring Data JPA** and **Hibernate** to interact with a **Microsoft SQL Server** database.

## Technologies Used

- **Spring Boot**: The core framework for building the application.
- **Spring Security**: For authentication and authorization, including OAuth2.
- **Spring Data JPA**: For data persistence.
- **Hibernate**: The JPA provider.
- **Microsoft SQL Server**: The database for the application.
- **Razorpay**: For payment gateway integration.
- **JasperReports**: For reporting.
- **Lombok**: To reduce boilerplate code.
- **MapStruct**: For mapping between different object models.

## Project Structure

The project is structured as follows:

- `src/main/java/com/iss/`: The root package for the source code.
  - `configs/`: Contains configuration classes for the application, such as security and caching.
  - `constrains/`: Defines custom constraints and validation rules.
  - `controllers/`: Handles incoming HTTP requests and defines the API endpoints.
  - `Dto/`: Contains Data Transfer Objects (DTOs) used for transferring data between different layers of the application.
  - `exceptionhandlers/`: Manages global exception handling for the application.
  - `Mappers/`: Includes MapStruct mappers for converting between DTOs and entities.
  - `models/`: Defines the entity classes that map to database tables.
  - `Repos/`: Contains the repository interfaces for data access.
  - `Services/`: Implements the business logic of the application.
  - `Validators/`: Provides custom validation logic.
- `src/main/resources/`: Contains application resources, such as configuration files and templates.
- `pom.xml`: The Maven project configuration file, which defines the project's dependencies and build settings.

## How It Works

1. **Client Authentication**: A client application obtains an OAuth2 access token from an authorization server.
2. **API Request**: The client sends a request to a protected endpoint in this application, including the access token in the `Authorization` header.
3. **Token Validation**: The application intercepts the request and validates the access token. If the token is valid, the request is allowed to proceed.
4. **Business Logic**: The corresponding controller and service handle the request, performing the required business logic, such as processing a payment or generating a report.
5. **Response**: The application sends a response back to the client.

This project provides a solid foundation for building a secure and scalable backend system with OAuth2-based security and integrated services for payments and reporting.