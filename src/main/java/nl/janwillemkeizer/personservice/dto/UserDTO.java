package nl.janwillemkeizer.personservice.dto;

import nl.janwillemkeizer.personservice.entity.Address;
import nl.janwillemkeizer.personservice.entity.Company;
import nl.janwillemkeizer.personservice.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
    private List<Long> postIds;
    private List<Long> albumIds;
    private List<Long> todoIds;

    public UserDTO() {
    }

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setPhone(user.getPhone());
        dto.setWebsite(user.getWebsite());
        dto.setCompany(user.getCompany());
        
        if (user.getPosts() != null) {
            dto.setPostIds(user.getPosts().stream()
                .map(post -> post.getId())
                .collect(Collectors.toList()));
        }
        
        if (user.getAlbums() != null) {
            dto.setAlbumIds(user.getAlbums().stream()
                .map(album -> album.getId())
                .collect(Collectors.toList()));
        }
        
        if (user.getTodos() != null) {
            dto.setTodoIds(user.getTodos().stream()
                .map(todo -> todo.getId())
                .collect(Collectors.toList()));
        }
        
        return dto;
    }

    // Getters and Setters
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

    public List<Long> getPostIds() {
        return postIds;
    }

    public void setPostIds(List<Long> postIds) {
        this.postIds = postIds;
    }

    public List<Long> getAlbumIds() {
        return albumIds;
    }

    public void setAlbumIds(List<Long> albumIds) {
        this.albumIds = albumIds;
    }

    public List<Long> getTodoIds() {
        return todoIds;
    }

    public void setTodoIds(List<Long> todoIds) {
        this.todoIds = todoIds;
    }
} 