package mk.ukim.finki.blogbusterbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.blogbusterbackend.model.enumerations.Role;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String biography;

    @CreatedDate
    private LocalDate registration_date;


    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author")
    private List<Reply> replies;

    @ManyToMany
    private List<User> followingUsers;

    @ManyToMany
    private List<Category> followingCategories;

    @ManyToMany(mappedBy = "likedByUsers")
    private List<Post> likedPosts;

    public User(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.replies = new ArrayList<>();
        this.followingUsers = new ArrayList<>();
        this.followingCategories = new ArrayList<>();
        this.likedPosts = new ArrayList<>();
        this.role = Role.ROLE_USER;

    }


}
