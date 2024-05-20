package mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String token;
    private String refreshToken;
    private Long userId;
}
