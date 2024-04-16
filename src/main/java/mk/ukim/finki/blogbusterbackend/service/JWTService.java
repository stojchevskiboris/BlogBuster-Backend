package mk.ukim.finki.blogbusterbackend.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Objects;

public interface JWTService {
    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    public String generateRefreshToken(Map<String, Objects> extraClaims, UserDetails userDetails);
}
