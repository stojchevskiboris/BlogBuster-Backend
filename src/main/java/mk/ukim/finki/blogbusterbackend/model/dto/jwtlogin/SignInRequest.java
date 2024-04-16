package mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin;

import lombok.Data;

@Data
public class SignInRequest {

    public String email;

    public String password;
}
