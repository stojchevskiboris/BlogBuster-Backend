package mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin;

import lombok.Data;

@Data
public class SignInRequest {

    public String username;

    public String password;
}
