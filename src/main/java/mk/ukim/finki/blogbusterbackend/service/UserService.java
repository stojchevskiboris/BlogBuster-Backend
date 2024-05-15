package mk.ukim.finki.blogbusterbackend.service;

import org.springframework.security.core.userdetails.UserDetailsService;



public interface UserService {
     UserDetailsService userDetailsService();

     void followUser(Long followerId, Long followeeId);

     void unfollowUser(Long loggedInUserId, Long followeeId);

     void followCategory(Long loggedInUserId, Long categoryId);

    void unfollowCategory(Long loggedInUserId, Long categoryId);


     Long getUserIdByEmail(String email);


}
