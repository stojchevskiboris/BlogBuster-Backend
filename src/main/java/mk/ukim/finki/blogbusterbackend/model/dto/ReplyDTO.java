package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
public class ReplyDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private Long commentId;
    private String firstname;
    private String lastname;
    private LocalDateTime reply_date;

    public ReplyDTO(Long id, @NonNull String content,  String authorUsername, @NonNull Long commentId, String firstname, String lastname, LocalDateTime reply_date) {
        this.id = id;
        this.content = content;
        this.authorUsername = authorUsername;
        this.commentId = commentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.reply_date = reply_date;
    }
}