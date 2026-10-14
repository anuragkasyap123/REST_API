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

## Lesson 2: CRUD and Request/Response DTOs

The task API demonstrates create, read, update, and delete using JSON:

| Operation | Request | Result |
| --- | --- | --- |
| Create | `POST /api/tasks` with a JSON body | `201 Created`, response body, and a `Location` header |
| List | `GET /api/tasks` | `200 OK` with an array |
| Read one | `GET /api/tasks/{id}` | `200 OK`, or `404 Not Found` |
| Replace | `PUT /api/tasks/{id}` with a JSON body | `200 OK`, or `404 Not Found` |
| Delete | `DELETE /api/tasks/{id}` | `204 No Content`, or `404 Not Found` |

Create a task from PowerShell:

```powershell
curl.exe -i -X POST "http://localhost:8080/api/tasks" -H "Content-Type: application/json" --data-raw '{"title":"Learn REST","completed":false}'
```

Use the returned `id` for the other operations:

```powershell
curl.exe "http://localhost:8080/api/tasks"
curl.exe "http://localhost:8080/api/tasks/1"
curl.exe -X PUT "http://localhost:8080/api/tasks/1" -H "Content-Type: application/json" --data-raw '{"title":"Learn Spring REST","completed":true}'
curl.exe -i -X DELETE "http://localhost:8080/api/tasks/1"
```

`CreateTaskRequest` and `UpdateTaskRequest` define the JSON clients send. `TaskResponse` defines the JSON the API returns, including the server-generated ID. These records are DTOs (Data Transfer Objects): they define the data crossing the HTTP boundary. `Task` is the application's stored representation. For this lesson, tasks live in memory and reset when the application restarts; we will move storage behind a repository in a later lesson.

The `PUT` endpoint replaces both editable fields. Task titles are required, cannot be blank, and may contain at most 200 characters. The API validates both create and replace requests before changing task data. For example, this request is rejected with `400 Bad Request`:

```powershell
curl.exe -i -X POST "http://localhost:8080/api/tasks" -H "Content-Type: application/json" --data-raw '{"title":"   ","completed":false}'
```

Errors use one JSON shape, including validation failures and missing tasks:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Request validation failed",
  "fieldErrors": {"title": "must not be blank"}
}
```

`fieldErrors` is an object keyed by request field; it is empty for errors that do not belong to a particular field, such as malformed JSON or a missing task. `ApiExceptionHandler` centralizes these responses so clients can handle errors consistently.

## Learning Path

1. HTTP methods, status codes, paths, and query parameters
2. CRUD endpoints and request/response DTOs
3. Validation and consistent error responses (task titles are nonblank and limited to 200 characters; errors share a JSON format)
4. Service and repository layers
5. Database persistence and migrations
6. Pagination, filtering, and API documentation
7. Authentication, authorization, and security
8. Integration tests, deployment, and production concerns
