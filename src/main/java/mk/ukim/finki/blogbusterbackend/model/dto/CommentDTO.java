package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;

@Getter
public class CommentDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private Long postId;

    public CommentDTO(Long id, String content, String authorUsername, Long postId) {
        this.id = id;
        this.content = content;
        this.authorUsername = authorUsername;
        this.postId = postId;
    }
}