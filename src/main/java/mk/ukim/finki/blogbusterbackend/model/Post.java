package mk.ukim.finki.blogbusterbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;


@Entity
@Data
@Table(name = "blog_posts")
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @CreatedDate
    private LocalDateTime creation_date;

    @LastModifiedDate
    private LocalDateTime modified_date;

    private Boolean isModified;

    @ManyToOne
    private User author;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToMany(mappedBy = "likedPosts")
    private List<User> likedByUsers;

    @Lob
    @Column(name = "image")
    private byte[] image;




    public Post(String title, String content, User author, Category category, byte[] image) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.comments = new ArrayList<>();
        this.likedByUsers = new ArrayList<>();
        this.image = image;
        this.isModified = false;
    }


}