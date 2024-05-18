package mk.ukim.finki.blogbusterbackend.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin.JwtAuthenticationResponse;
import mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin.RefreshTokenRequest;
import mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin.SignInRequest;
import mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin.SignUpRequest;
import mk.ukim.finki.blogbusterbackend.model.enumerations.Role;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;
import mk.ukim.finki.blogbusterbackend.service.AuthenticationService;
import mk.ukim.finki.blogbusterbackend.service.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public User signup(SignUpRequest signUpRequest) {
        User user = new User();

        user.setEmail(signUpRequest.getEmail());
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));



//        if (signUpRequest.getImage() != null) {
//            String fileName = StringUtils.cleanPath(signUpRequest.getImage().getOriginalFilename());
//            try {
//                user.setImage(signUpRequest.getImage().getBytes());
//            } catch (IOException e) {
//            }
//        }

        return userRepository.save(user);



    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;

    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }
        return null;
    }
}
