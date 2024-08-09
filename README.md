BookMyEvent - An Event Ticket Booking Application

Architecture Diagram
- https://drive.google.com/file/d/1EBvdvsS2ODw7FDfQMlPHehJ1Moy79Sga/view?usp=sharing

BookMyEvent is a microservices-based platform designed for booking movie tickets. The application is built with
scalability, security, and efficiency in mind, addressing challenges such as concurrency, secure authentication, and 
service discovery. Below is an overview of the architecture and key components of the application.

Architecture Overview
The BookMyEvent application follows a microservices architecture, where each service is responsible for a specific 
domain function. This modular approach ensures that the application can scale efficiently and be maintained with ease.

Key Components
- Auth Service 
- Link - https://github.com/Vivek-Bansal04/auth-service
    Manages user registration, and login functionalities.
    Handles secure authentication using JWT tokens and OTP (One-Time Password) for two-factor authentication.

- BMS Service
- Link https://github.com/Vivek-Bansal04/BookMyEvent
  Manages movie events, including details like movie names, showtimes, and available seats.
  Provides APIs to book movie tickets, add theatres, cities and check seat availability.
  Handles ticket booking, ensuring that seats are allocated correctly.
  Manages the concurrency issues that arise when multiple users attempt to book the same tickets simultaneously.

- Spring Cloud Gateway 
- Link - https://github.com/Vivek-Bansal04/api-gateway-bms
  Acts as the entry point to the system, routing requests to the appropriate microservice.
  Handles cross-cutting concerns like authentication
  All incoming requests call auth service to validate token, ensuring that only authenticated users can 
  access the application. Then they call respective microservice.

- Service Discovery with Eureka
- Link - https://github.com/Vivek-Bansal04/eureka-service-discovery
  Implements service discovery using Eureka, allowing microservices to find and communicate with each other dynamically.
  Ensures high availability and load balancing across services, improving overall system reliability.

