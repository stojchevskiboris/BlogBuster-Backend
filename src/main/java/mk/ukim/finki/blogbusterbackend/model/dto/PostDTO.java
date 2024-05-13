package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private String categoryName;
    private byte[] image;
    private LocalDate creation_date;
    private LocalDate modified_date;
    private boolean isModified;


    public PostDTO(Long id, @NonNull String title, @NonNull String content, @NonNull String authorUsername, String categoryName, byte[] image, LocalDate creation_date, LocalDate modified_date, boolean isModified) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorUsername = authorUsername;
        this.categoryName = categoryName;
        this.image = image;
        this.creation_date = creation_date;
        this.modified_date = modified_date;
        this.isModified = isModified;
    }
}