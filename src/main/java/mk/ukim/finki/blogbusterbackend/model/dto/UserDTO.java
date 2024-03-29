package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;

@Getter
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String biography;


}