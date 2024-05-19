package mk.ukim.finki.blogbusterbackend.service;

import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService {
    List<UserDTO> discoverPeople(Long userId);

    UserDetailsService userDetailsService();

    void followUser(Long followerId, Long followeeId);

    void unfollowUser(Long loggedInUserId, Long followeeId);

    void followCategory(Long loggedInUserId, Long categoryId);

    void unfollowCategory(Long loggedInUserId, Long categoryId);

    Boolean likePost(Long loggedInUserId, Long postId);

    Boolean unlikePost(Long loggedInUserId, Long postId);

    Long getUserIdByEmail(String email);

    List<UserDTO> getFollowers(Long userId);

    List<Long> getLikedPosts(Long userId);

    UserDTO getUserDetails();
}
