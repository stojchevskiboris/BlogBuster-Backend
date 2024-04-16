package mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin;

import lombok.Data;

@Data
public class RefreshTokenRequest {

    private String token;
}
