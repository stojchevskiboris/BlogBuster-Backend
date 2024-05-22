package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private String categoryName;
    private Long imageId;
    private LocalDateTime creation_date;
    private LocalDateTime modified_date;
    private boolean isModified;

    private String firstname;
    private String lastname;


    public PostDTO(Long id,  String title,  String content,  String authorUsername, String firstname, String lastname,
                   String categoryName, Long imageId, LocalDateTime creation_date, LocalDateTime modified_date,
                   boolean isModified) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorUsername = authorUsername;
        this.firstname = firstname;
        this.lastname = lastname;
        this.categoryName = categoryName;
        this.imageId = imageId;
        this.creation_date = creation_date;
        this.modified_date = modified_date;
        this.isModified = isModified;
    }
//public PostDTO(Long id,  String title,  String content,  String authorUsername, String firstname, String lastname,
//               String categoryName, LocalDateTime creation_date, LocalDateTime modified_date,
//               boolean isModified) {
//    this.id = id;
//    this.title = title;
//    this.content = content;
//    this.authorUsername = authorUsername;
//    this.firstname = firstname;
//    this.lastname = lastname;
//    this.categoryName = categoryName;
//    this.creation_date = creation_date;
//    this.modified_date = modified_date;
//    this.isModified = isModified;
//}
}