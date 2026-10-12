# REST API Learning Project

This project builds a REST API step by step with Java 25, Maven, and Spring Boot.

## Lesson 1: HTTP Methods, Paths, Parameters, and Status Codes

Start the application from PowerShell after configuring `JAVA_HOME` to point to your JDK 25 installation:

```powershell
mvn spring-boot:run
```

The same greeting can be requested with a query parameter:

```powershell
curl.exe "http://localhost:8080/api/greetings?name=Anurag"
```

Or with the name as part of the path:

```powershell
curl.exe "http://localhost:8080/api/greetings/Anurag"
```

Both return `200 OK` with this JSON response:

```json
{"message":"Hello, Anurag!"}
```

In `/api/greetings?name=Anurag`, `/api/greetings` is the path and `name=Anurag` is a query parameter. Query parameters are a good fit for optional inputs such as search and filtering. In `/api/greetings/Anurag`, `Anurag` is a path variable that identifies part of the resource path. Omit the query parameter to use the default name, `world`.

### HTTP method and status code basics

| Method | Typical purpose | Common success status |
| --- | --- | --- |
| `GET` | Read data | `200 OK` |
| `POST` | Create data | `201 Created` |
| `PUT` | Replace data | `200 OK` or `204 No Content` |
| `PATCH` | Partially update data | `200 OK` or `204 No Content` |
| `DELETE` | Delete data | `204 No Content` |

Other useful statuses include `400 Bad Request` for invalid input, `404 Not Found` when a resource does not exist, and `500 Internal Server Error` for an unexpected server failure. Our greeting endpoint is a `GET`, so Spring returns `200 OK` when it finds the route and successfully builds the response.

### What the code is doing

- `@SpringBootApplication` starts Spring and enables its automatic configuration.
- `@RestController` declares a class whose method results are written to the HTTP response body.
- `@GetMapping` maps an HTTP GET request to a Java method.
- `@RequestParam` reads a query-string value; `defaultValue` makes the parameter optional.
- `@PathVariable` reads a value embedded in the URL path, such as `Anurag` in `/api/greetings/Anurag`.
- `GreetingResponse` is a Java record. Spring serializes it as JSON, so its `message` component becomes the `message` JSON property.
- `GreetingControllerTest` sends requests through Spring MVC without opening a network port and checks the status and JSON response.

Run the tests with:

```powershell
mvn clean test
```

## Learning Path

1. HTTP methods, status codes, paths, and query parameters
2. CRUD endpoints and request/response DTOs
3. Validation and consistent error responses
4. Service and repository layers
5. Database persistence and migrations
6. Pagination, filtering, and API documentation
7. Authentication, authorization, and security
8. Integration tests, deployment, and production concerns
