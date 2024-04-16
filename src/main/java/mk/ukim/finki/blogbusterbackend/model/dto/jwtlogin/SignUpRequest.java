package mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin;

import lombok.Data;

@Data
public class SignUpRequest {


    private String firstname;

    private String lastname;

    private String email;

    private String password;

}
