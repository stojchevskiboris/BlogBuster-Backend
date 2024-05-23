package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ImageDTO {
    private Long id;
    private byte[] picture;
    private Long postId;
    private String authorUsername;
    private String pictureBytesString;

    public ImageDTO(Long id, byte[] picture,String authorUsername,@NonNull Long postId, String pictureBytesString) {
        this.id = id;
        this.picture = picture;
        this.postId = postId;
        this.authorUsername=authorUsername;
        this.pictureBytesString = pictureBytesString;
    }
}
