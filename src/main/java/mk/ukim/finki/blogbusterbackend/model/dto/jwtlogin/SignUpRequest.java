package mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SignUpRequest {


    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private MultipartFile image;

}
