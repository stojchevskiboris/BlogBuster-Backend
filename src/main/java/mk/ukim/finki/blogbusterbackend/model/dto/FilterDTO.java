package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FilterDTO {
    Long categoryId;
    String authorUsername;
    LocalDateTime from;
    LocalDateTime to;
    String title;
    String context;

    public FilterDTO(Long categoryId, String authorUsername, LocalDateTime from, LocalDateTime to, String title, String context) {
        this.categoryId = categoryId;
        this.authorUsername = authorUsername;
        this.from = from;
        this.to = to;
        this.title = title;
        this.context = context;
    }
}
