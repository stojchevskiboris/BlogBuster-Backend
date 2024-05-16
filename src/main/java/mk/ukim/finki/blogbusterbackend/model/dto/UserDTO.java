package mk.ukim.finki.blogbusterbackend.model.dto;

import lombok.Getter;

@Getter
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;

    public UserDTO(Long id, String firstname, String lastname, String username) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
    }
}
