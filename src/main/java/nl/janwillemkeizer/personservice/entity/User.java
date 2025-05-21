package nl.janwillemkeizer.personservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Schema(description = "User entity containing user information")
public class User {

    @Id
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Column(name = "user_name")
    @Schema(description = "Full name of the user", example = "John Doe")
    private String name;

    @Schema(description = "Username for login", example = "johndoe")
    private String username;

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @Embedded
    @Schema(description = "Address details of the user")
    private Address address;

    @Schema(description = "Phone number of the user", example = "555-123-4567")
    private String phone;

    @Schema(description = "Website of the user", example = "www.johndoe.com")
    private String website;

    @Embedded
    @Schema(description = "Company details of the user")
    private Company company;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "List of posts created by the user")
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "List of albums created by the user")
    @JsonManagedReference
    private List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "List of todos created by the user")
    @JsonManagedReference
    private List<Todo> todos = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    public void addAlbum(Album album) {
        albums.add(album);
        album.setUser(this);
    }

    public void addTodo(Todo todo) {
        todos.add(todo);
        todo.setUser(this);
    }
} 