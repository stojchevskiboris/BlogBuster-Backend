package mk.ukim.finki.blogbusterbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "blog_categories")
@ToString(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany
    private List<Post> posts;

    @ManyToMany(mappedBy = "followingCategories")
    private List<User> followers;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.posts = new ArrayList<>();
        this.followers = new ArrayList<>();
    }

    public Category(String name) {
        this.name = name;
        this.posts = new ArrayList<>();
        this.followers = new ArrayList<>();
    }

}
