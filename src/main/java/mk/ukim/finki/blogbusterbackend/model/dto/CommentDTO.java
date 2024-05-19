package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
public class CommentDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private Long postId;
    private LocalDateTime comment_date;


    private String firstname;
    private String lastname;

    public CommentDTO(Long id, @NonNull String content,  String authorUsername, @NonNull Long postId, LocalDateTime comment_date,
                      String firstname, String lastname) {
        this.id = id;
        this.content = content;
        this.authorUsername = authorUsername;
        this.postId = postId;
        this.comment_date = comment_date;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}