package mk.ukim.finki.blogbusterbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "blog_replies")
@RequiredArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @CreatedDate
    private LocalDateTime reply_date;

    @ManyToOne
    private User author;

    @ManyToOne
    private Comment comment;


    public Reply(String content, User author, Comment comment) {
        this.content = content;
        this.author = author;
        this.comment = comment;
    }




}