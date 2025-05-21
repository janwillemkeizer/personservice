package nl.janwillemkeizer.personservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.janwillemkeizer.personservice.entity.Post;
import nl.janwillemkeizer.personservice.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Post", description = "Post management endpoints")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get posts by user ID", description = "Returns all posts for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "No posts found for user", content = @Content)
    })
    public ResponseEntity<List<Post>> getPostsByUserId(
            @Parameter(description = "ID of the user whose posts to retrieve") @PathVariable Long userId) {
        logger.info("Fetching posts for user ID: {}", userId);
        List<Post> posts = postRepository.findByUser_Id(userId);
        // Clear related entities to prevent circular references
        posts.forEach(post -> {
            post.setUser(null);
        });
        logger.info("Found {} posts for user ID: {}", posts.size(), userId);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
} 