package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;

@Getter
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private String categoryName;


}