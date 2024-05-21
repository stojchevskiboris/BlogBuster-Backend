package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;

@Getter
public class AddPostDTO {
    private PostDTO data;
    private String userId;

    public AddPostDTO(PostDTO data, String userId) {
        this.data = data;
        this.userId = userId;
    }
}


