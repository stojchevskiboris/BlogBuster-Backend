package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ImageDTO {
    private Long id;
    private byte[] picture;
    private Long postId;
    private String authorUsername;

    public ImageDTO(Long id, byte[] picture,String authorUsername,@NonNull Long postId) {
        this.id = id;
        this.picture = picture;
        this.postId = postId;
        this.authorUsername=authorUsername;
    }
}
