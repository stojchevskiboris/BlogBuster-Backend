package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;

@Getter
public class AddPostDTO {
    private PostDTO postDTO;
    private String image;
    private String userId;

    public AddPostDTO(PostDTO postDTO, String image, String userId) {
        this.postDTO = postDTO;
        this.image = image;
        this.userId = userId;
    }
}


