package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;

@Getter
public class ReplyDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private Long commentId;

    public ReplyDTO(Long id, String content, String authorUsername, Long commentId) {
        this.id = id;
        this.content = content;
        this.authorUsername = authorUsername;
        this.commentId = commentId;
    }
}