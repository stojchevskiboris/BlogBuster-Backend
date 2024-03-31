package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;

@Getter
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private String categoryName;
    private byte[] image;


    public PostDTO(Long id, String title, String content, String authorUsername, String categoryName, byte[] image) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorUsername = authorUsername;
        this.categoryName = categoryName;
        this.image = image;
    }
}