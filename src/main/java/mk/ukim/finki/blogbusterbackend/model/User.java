package mk.ukim.finki.blogbusterbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import mk.ukim.finki.blogbusterbackend.model.enumerations.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Entity
@Table(name = "_user")
@ToString(onlyExplicitlyIncluded = true)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "replies")
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies;

    @Column(name = "users")
    @ManyToMany
    private List<User> followingUsers;

    @Column(name = "categories")
    @ManyToMany
    private List<Category> followingCategories;

    @Column(name = "likedPosts")
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Post> likedPosts;





    public User(String firstName, String lastName, String username, String password, byte[] image) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.username = username;
        this.password = password;
        this.replies = new ArrayList<>();
        this.followingUsers = new ArrayList<>();
        this.followingCategories = new ArrayList<>();
        this.likedPosts = new ArrayList<>();
        this.role = Role.ROLE_USER;
        this.image = image;
    }

    public User(){}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
