package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;

@Getter
public class ReplyDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private Long commentId;


}