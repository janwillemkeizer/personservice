package nl.janwillemkeizer.personservice.service;

import nl.janwillemkeizer.personservice.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users";
    private static final String POSTS_ENDPOINT = "/posts";
    private static final String TODOS_ENDPOINT = "/todos";
    private static final String ALBUMS_ENDPOINT = "/albums";
    private static final String COMMENTS_ENDPOINT = "/comments";
    private static final String PHOTOS_ENDPOINT = "/photos";
    
    private final RestClient restClient;
    private final AtomicInteger totalRequestsCounter = new AtomicInteger(0);
    private final AtomicInteger successfulRequestsCounter = new AtomicInteger(0);
    private final AtomicInteger failedRequestsCounter = new AtomicInteger(0);

    public PersonService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public List<UserDto> getAllUsersWithDetails() {
        logger.info("Starting to fetch all users with details");
        Instant start = Instant.now();
        
        List<UserDto> users = fetchResource(USERS_ENDPOINT, "USERS", new ParameterizedTypeReference<>() {});
        List<UserDto> enrichedUsers = users.stream()
                .map(this::enrichUserWithDetails)
                .toList();
                
        Duration duration = Duration.between(start, Instant.now());
        logger.info("Completed fetching all users with details in {} ms, total requests: {}, successful: {}, failed: {}", 
                duration.toMillis(), totalRequestsCounter.get(), successfulRequestsCounter.get(), failedRequestsCounter.get());
        
        return enrichedUsers;
    }

    private UserDto enrichUserWithDetails(UserDto user) {
        Long userId = user.getId();
        String userResourcePath = USERS_ENDPOINT + "/" + userId;
        
        // Fetch and set user's posts with comments
        Instant postsStart = Instant.now();
        List<PostDto> posts = fetchResource(userResourcePath + POSTS_ENDPOINT, "POSTS", new ParameterizedTypeReference<>() {});
        
        Instant commentsStart = Instant.now();
        posts.forEach(this::enrichPostWithComments);
        Duration commentsDuration = Duration.between(commentsStart, Instant.now());
        logger.info("Fetched comments for {} posts in {} ms", posts.size(), commentsDuration.toMillis());
        
        user.setPosts(posts);
        Duration postsDuration = Duration.between(postsStart, Instant.now());
        logger.info("Fetched posts with comments for user {} in {} ms", userId, postsDuration.toMillis());

        // Fetch and set user's todos
        Instant todosStart = Instant.now();
        List<TodoDto> todos = fetchResource(userResourcePath + TODOS_ENDPOINT, "TODOS", new ParameterizedTypeReference<>() {});
        user.setTodos(todos);
        Duration todosDuration = Duration.between(todosStart, Instant.now());
        logger.info("Fetched todos for user {} in {} ms", userId, todosDuration.toMillis());

        // Fetch and set user's albums with photos
        Instant albumsStart = Instant.now();
        List<AlbumDto> albums = fetchResource(userResourcePath + ALBUMS_ENDPOINT, "ALBUMS", new ParameterizedTypeReference<>() {});
        
        Instant photosStart = Instant.now();
        albums.forEach(this::enrichAlbumWithPhotos);
        Duration photosDuration = Duration.between(photosStart, Instant.now());
        logger.info("Fetched photos for {} albums in {} ms", albums.size(), photosDuration.toMillis());
        
        user.setAlbums(albums);
        Duration albumsDuration = Duration.between(albumsStart, Instant.now());
        logger.info("Fetched albums with photos for user {} in {} ms", userId, albumsDuration.toMillis());
        
        return user;
    }

    private void enrichPostWithComments(PostDto post) {
        String postResourcePath = POSTS_ENDPOINT + "/" + post.getId();
        List<CommentDto> comments = fetchResource(postResourcePath + COMMENTS_ENDPOINT, "COMMENTS", 
                new ParameterizedTypeReference<>() {});
        post.setComments(comments);
    }

    private void enrichAlbumWithPhotos(AlbumDto album) {
        String albumResourcePath = ALBUMS_ENDPOINT + "/" + album.getId();
        List<PhotoDto> photos = fetchResource(albumResourcePath + PHOTOS_ENDPOINT, "PHOTOS", 
                new ParameterizedTypeReference<>() {});
        album.setPhotos(photos);
    }
    
    private <T> List<T> fetchResource(String path, String resourceType, ParameterizedTypeReference<List<T>> typeReference) {
        Instant start = Instant.now();
        int currentRequestCount = totalRequestsCounter.incrementAndGet();
        
        try {
            ResponseEntity<List<T>> responseEntity = restClient.get()
                .uri(BASE_URL + path)
                .retrieve()
                .toEntity(typeReference);
                
            var statusCode = responseEntity.getStatusCode();
            boolean isSuccessful = statusCode.is2xxSuccessful();
            
            if (isSuccessful) {
                successfulRequestsCounter.incrementAndGet();
            } else {
                failedRequestsCounter.incrementAndGet();
            }
            
            Duration duration = Duration.between(start, Instant.now());
            logger.info("Fetched {} resource from {} in {} ms, status: {}, total requests: {}, successful: {}, failed: {}", 
                    resourceType, path, duration.toMillis(), statusCode.value(), 
                    currentRequestCount, successfulRequestsCounter.get(), failedRequestsCounter.get());
            
            return responseEntity.getBody() != null ? responseEntity.getBody() : Collections.emptyList();
        } catch (Exception e) {
            failedRequestsCounter.incrementAndGet();
            Duration duration = Duration.between(start, Instant.now());
            logger.error("Failed to fetch {} resource from {} in {} ms, error: {}, total requests: {}, successful: {}, failed: {}", 
                    resourceType, path, duration.toMillis(), e.getMessage(), 
                    currentRequestCount, successfulRequestsCounter.get(), failedRequestsCounter.get());
            return Collections.emptyList();
        }
    }
} 