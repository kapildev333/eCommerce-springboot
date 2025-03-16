# E-commerce Application

This is a Spring Boot-based e-commerce application that provides user authentication, profile management, and shipping address management.

## Features

- User Sign Up
- User Sign In
- User Profile Update
- User Deletion
- Shipping Address Management

## Technologies Used

- Java
- Spring Boot
- Spring Security
- Hibernate
- JPA
- Maven
- JWT (JSON Web Token)
- Jakarta Persistence API
- RESTful APIs

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- Docker
- An IDE like IntelliJ IDEA

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/kapildev333/ecommerce.git
    cd ecommerce
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

### Docker

To run the application using Docker:

1. Build the Docker image:
    ```sh
    docker build -t ecommerce-app .
    ```

2. Run the Docker container:
    ```sh
    docker run -p 8080:8080 ecommerce-app
    ```

### API Endpoints

#### User Authentication

- **Sign Up**
    ```http
    POST /api/user/v1/signup
    Content-Type: multipart/form-data
    ```

    **Parameters:**
    - `firstName`: String
    - `lastName`: String
    - `email`: String
    - `password`: String
    - `profilePicture`: MultipartFile

- **Sign In**
    ```http
    POST /api/user/v1/signin
    Content-Type: application/x-www-form-urlencoded
    ```

    **Parameters:**
    - `email`: String
    - `password`: String

- **Update User**
    ```http
    PUT /api/user/v1/update
    Content-Type: multipart/form-data
    ```

    **Parameters:**
    - `firstName`: String
    - `lastName`: String
    - `newPassword`: String
    - `profilePicture`: MultipartFile

- **Delete User**
    ```http
    DELETE /api/user/v1/delete
    ```

### Shipping Address Management

- **Add Shipping Address**
    ```http
    POST /api/shipping-address
    Content-Type: application/json
    ```

    **Body:**
    ```json
    {
        "type": "Home",
        "street": "123 Main St",
        "city": "Anytown",
        "state": "Anystate",
        "postalCode": "12345",
        "country": "USA"
    }
    ```

## Project Structure

- `src/main/java/org/kapildev333/ecommerce/models/ShippingAddressRequestModel.java`: Model for shipping address requests.
- `src/main/java/org/kapildev333/ecommerce/features/authentications/User.java`: Entity representing a user.
- `src/main/java/org/kapildev333/ecommerce/features/authentications/UserController.java`: Controller for user-related operations.

## License

This project is licensed under the MIT License.