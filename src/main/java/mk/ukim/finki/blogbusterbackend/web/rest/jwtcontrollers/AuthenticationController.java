package mk.ukim.finki.blogbusterbackend.web.rest.jwtcontrollers;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin.JwtAuthenticationResponse;
import mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin.RefreshTokenRequest;
import mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin.SignInRequest;
import mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin.SignUpRequest;
import mk.ukim.finki.blogbusterbackend.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;




    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) {
        //SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFirstname(signUpRequest.getFirstname());
        signUpRequest.setLastname(signUpRequest.getLastname());
        signUpRequest.setUsername(signUpRequest.getUsername());
        signUpRequest.setPassword(signUpRequest.getPassword());
        //signUpRequest.setImage(image);

        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }


}