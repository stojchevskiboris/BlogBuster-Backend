package mk.ukim.finki.blogbusterbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;


@Entity
@Data
@Table(name = "blog_posts")
@ToString(onlyExplicitlyIncluded = true)
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToMany(mappedBy = "likedPosts", cascade = CascadeType.ALL)
    private List<User> likedByUsers;

//    @Lob
//    @Column(name = "image", nullable = true)
//    private byte[] image;

    @OneToOne
    private Image image;




    public Post(String title, String content, User author, Category category, Image image) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.comments = new ArrayList<>();
        this.likedByUsers = new ArrayList<>();
        this.image = image;
        this.isModified = false;
    }

//    public Post(String title, String content, User author, Category category) {
//        this.title = title;
//        this.content = content;
//        this.author = author;
//        this.category = category;
//        this.comments = new ArrayList<>();
//        this.likedByUsers = new ArrayList<>();
//        this.images=new ArrayList<>();
//        this.isModified = false;
//    }

}