package mk.ukim.finki.blogbusterbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "blog_categories")
@RequiredArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private List<Post> posts;

    @ManyToMany(mappedBy = "followingCategories")
    private List<User> followers;

    public Category(String name) {
        this.name = name;
        this.posts = new ArrayList<>();
        this.followers = new ArrayList<>();
    }


}