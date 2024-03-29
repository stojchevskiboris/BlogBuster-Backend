package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;

@Getter
public class CommentDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private Long postId;

}