package main.java.com.example.restapi;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody CreateTaskRequest request) {
        long id = nextId.getAndIncrement();
        Task task = new Task(id, request.title(), request.completed());
        tasks.put(id, task);

        return ResponseEntity.created(URI.create("/api/tasks/" + id))
                .body(toResponse(task));
    }

    @GetMapping
    public List<TaskResponse> findAll() {
        return tasks.values().stream()
                .sorted(Comparator.comparingLong(Task::id))
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> findById(@PathVariable long id) {
        Task task = tasks.get(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> replace(
            @PathVariable long id,
            @RequestBody UpdateTaskRequest request) {
        Task task = tasks.computeIfPresent(id,
                (taskId, existing) -> new Task(taskId, request.title(), request.completed()));
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        if (tasks.remove(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(task.id(), task.title(), task.completed());
    }
}