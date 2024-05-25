package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.ReplyDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.UserDTO;
import mk.ukim.finki.blogbusterbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")

public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/{followeeId}/followUser")
    public ResponseEntity<String> followUser(@PathVariable Long followeeId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long loggedInUserId = userService.getUserIdByUsername(username);

        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        userService.followUser(loggedInUserId, followeeId);
        return ResponseEntity.ok("User successfully followed.");
    }


    @PostMapping("/{followeeId}/unfollowUser")
    public ResponseEntity<String> unfollowUser(@PathVariable Long followeeId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long loggedInUserId = userService.getUserIdByUsername(username);

        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        userService.unfollowUser(loggedInUserId, followeeId);
        return ResponseEntity.ok("User successfully unfollowed.");
    }

    @PostMapping("/{categoryId}/followCategory")
    public ResponseEntity<String> followCategory(@PathVariable Long categoryId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long loggedInUserId = userService.getUserIdByUsername(username);

        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        userService.followCategory(loggedInUserId, categoryId);
        return ResponseEntity.ok("Category successfully followed.");

    }

    @PostMapping("/{categoryId}/unfollowCategory")
    public ResponseEntity<String> unfollowCategory(@PathVariable Long categoryId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long loggedInUserId = userService.getUserIdByUsername(username);

        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        userService.unfollowCategory(loggedInUserId, categoryId);

        return ResponseEntity.ok("Category successfully unfollowed.");
    }


    @GetMapping("/test")
    public String testConnection() {
        return "Backend connection successful!";
    }

    @GetMapping("/following/{id}")
    public List<UserDTO> getFollowers(@PathVariable Long id)
    {
        return this.userService.getFollowers(id);
    }

    @GetMapping("/details")
    public UserDTO details(){
        return this.userService.getUserDetails();
    }

    @GetMapping("/discoverUsers/{id}")
    public List<UserDTO> discoverUsers(@PathVariable Long id){
        return this.userService.discoverPeople(id);
    }

    @GetMapping("/getTotalPostsByUserId/{id}")
    public int getTotalPostsByUserId(@PathVariable Long id){
        return userService.getTotalPostsByUserId(id);
    }

    @GetMapping("/getTotalFollowersByUserId/{id}")
    public int getTotalFollowersByUserId(@PathVariable Long id){
        return userService.getTotalFollowersByUserId(id);
    }

    @GetMapping("/searchUsers")
    public List<UserDTO> searchUsers(@RequestParam String context)
    {
        return this.userService.searchUsers(context);
    }
}

