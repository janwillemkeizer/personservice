package nl.janwillemkeizer.personservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.janwillemkeizer.personservice.entity.Album;
import nl.janwillemkeizer.personservice.repository.AlbumRepository;
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
@RequestMapping("/api/albums")
@Tag(name = "Album", description = "Album management endpoints")
public class AlbumController {
    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumController(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get albums by user ID", description = "Returns all albums for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved albums",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Album.class))),
            @ApiResponse(responseCode = "404", description = "No albums found for user", content = @Content)
    })
    public ResponseEntity<List<Album>> getAlbumsByUserId(
            @Parameter(description = "ID of the user whose albums to retrieve") @PathVariable Long userId) {
        logger.info("Fetching albums for user ID: {}", userId);
        List<Album> albums = albumRepository.findByUser_Id(userId);
        // Clear related entities to prevent circular references
        albums.forEach(album -> {
            album.setUser(null);
        });
        logger.info("Found {} albums for user ID: {}", albums.size(), userId);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }
} 