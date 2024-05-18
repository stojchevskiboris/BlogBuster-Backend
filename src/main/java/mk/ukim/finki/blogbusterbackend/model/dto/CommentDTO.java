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

    public CommentDTO(Long id, @NonNull String content,  String authorUsername, @NonNull Long postId, LocalDateTime comment_date) {
        this.id = id;
        this.content = content;
        this.authorUsername = authorUsername;
        this.postId = postId;
        this.comment_date = comment_date;
    }
}