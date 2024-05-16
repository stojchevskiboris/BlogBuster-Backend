package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ReplyDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private Long commentId;

    public ReplyDTO(Long id, @NonNull String content, @NonNull String authorUsername, @NonNull Long commentId) {
        this.id = id;
        this.content = content;
        this.authorUsername = authorUsername;
        this.commentId = commentId;
    }
}