package mk.ukim.finki.blogbusterbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.blogbusterbackend.model.dto.CommentDTO;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;


@Entity
@Data
@Table(name = "blog_comments")
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @CreatedDate
    private LocalDate comment_date;

    @ManyToOne
    private User author;

    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<Reply> replies;


    public Comment(String content, User author, Post post) {
        this.content = content;
        this.author = author;
        this.post = post;
        this.replies = new ArrayList<>();
    }



}