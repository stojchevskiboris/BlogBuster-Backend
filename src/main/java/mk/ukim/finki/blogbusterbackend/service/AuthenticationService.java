package mk.ukim.finki.blogbusterbackend.service;

import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin.JwtAuthenticationResponse;
import mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin.RefreshTokenRequest;
import mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin.SignInRequest;
import mk.ukim.finki.blogbusterbackend.model.dto.jwtlogin.SignUpRequest;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
