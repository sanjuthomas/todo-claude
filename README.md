# TODO Application

A simple Spring Boot TODO application with Thymeleaf template engine.

## Features

- ✅ Create, read, update, and delete TODOs
- ✅ Mark TODOs as completed
- ✅ Add descriptions to TODOs
- ✅ Modern, responsive UI
- ✅ H2 in-memory database
- ✅ Single deployable JAR file

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Building the Application

```bash
mvn clean package
```

This will create a JAR file in the `target` directory: `todo-app-1.0.0.jar`

## Running the Application

### Option 1: Using Maven

```bash
mvn spring-boot:run
```

### Option 2: Running the JAR file

```bash
java -jar target/todo-app-1.0.0.jar
```

## Accessing the Application

Once the application is running, open your browser and navigate to:

```
http://localhost:8080
```

## Project Structure

```
todo-app/
├── src/
│   ├── main/
│   │   ├── java/com/example/todo/
│   │   │   ├── controller/    # REST controllers
│   │   │   ├── model/         # Entity classes
│   │   │   ├── repository/    # JPA repositories
│   │   │   ├── service/       # Business logic
│   │   │   └── TodoApplication.java
│   │   ├── resources/
│   │   │   ├── static/css/    # CSS stylesheets
│   │   │   ├── templates/     # Thymeleaf templates
│   │   │   └── application.properties
│   └── test/                  # Test files
├── pom.xml                    # Maven configuration
└── README.md
```

## API Endpoints

- `GET /` - View all TODOs
- `POST /add` - Create a new TODO
- `POST /toggle/{id}` - Toggle TODO completion status
- `POST /delete/{id}` - Delete a TODO
- `GET /edit/{id}` - Edit TODO page
- `POST /update/{id}` - Update a TODO

## Database

The application uses H2 in-memory database by default, which means data will be lost when the application restarts. To use a persistent database like MySQL, modify the `application.properties` file accordingly.

To access H2 console: `http://localhost:8080/h2-console`

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Template Engine**: Thymeleaf
- **Database**: H2 (in-memory)
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven

## Future Enhancements

- User authentication and authorization
- Due dates and reminders
- Categories/tags for TODOs
- Priority levels
- Search and filter functionality
- User profiles and persistent storage

## License

This project is open source and available under the MIT License.
