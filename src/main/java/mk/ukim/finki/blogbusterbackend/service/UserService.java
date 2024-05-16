package mk.ukim.finki.blogbusterbackend.service;

import mk.ukim.finki.blogbusterbackend.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService {
     UserDetailsService userDetailsService();

     void followUser(Long followerId, Long followeeId);

     void unfollowUser(Long loggedInUserId, Long followeeId);

     void followCategory(Long loggedInUserId, Long categoryId);

    void unfollowCategory(Long loggedInUserId, Long categoryId);


     Long getUserIdByEmail(String email);


}
