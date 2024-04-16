package mk.ukim.finki.blogbusterbackend.service;

import org.springframework.security.core.userdetails.UserDetailsService;



public interface UserService {
    UserDetailsService userDetailsService();

}
