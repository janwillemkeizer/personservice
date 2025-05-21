package nl.janwillemkeizer.personservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.janwillemkeizer.personservice.entity.User;
import nl.janwillemkeizer.personservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User management endpoints")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all available users without their related entities")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    public ResponseEntity<List<User>> getAllUsers() {
        Thread currentThread = Thread.currentThread();
        logger.info("Fetching all users on thread: {} (isVirtual: {})", 
            currentThread.getName(), 
            currentThread.isVirtual());
        List<User> users = userRepository.findAll();
        // Clear related entities to prevent circular references
        users.forEach(user -> {
            user.setPosts(null);
            user.setAlbums(null);
            user.setTodos(null);
        });
        logger.info("Found {} users", users.size());
        try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
} 