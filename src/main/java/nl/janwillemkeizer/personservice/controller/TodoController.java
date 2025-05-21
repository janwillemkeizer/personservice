package nl.janwillemkeizer.personservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.janwillemkeizer.personservice.entity.Todo;
import nl.janwillemkeizer.personservice.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todo", description = "Todo management endpoints")
public class TodoController {
    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    private final TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get todos by user ID", description = "Returns all todos for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved todos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class))),
        @ApiResponse(responseCode = "404", description = "No todos found for user", content = @Content)
    })
    public ResponseEntity<List<Todo>> getTodosByUserId(
            @Parameter(description = "ID of the user whose todos to retrieve") @PathVariable Long userId) {
        logger.info("Fetching todos for user ID: {}", userId);
        List<Todo> todos = todoRepository.findByUser_Id(userId);
        // Clear related entities to prevent circular references
        todos.forEach(todo -> todo.setUser(null));
        logger.info("Found {} todos for user ID: {}", todos.size(), userId);
        try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }
} 