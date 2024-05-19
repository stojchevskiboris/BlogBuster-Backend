package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.service.PostService;
import mk.ukim.finki.blogbusterbackend.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostRestController {
    private final PostService postService;
    private final UserService userService;


    public PostRestController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;

    }

    @GetMapping(value = {"","/", "/list"})
    public List<PostDTO> listPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping("/add")
    public boolean addPost(@RequestBody PostDTO postDto) throws Exception {
        this.postService.addPost(postDto);
        return true;
    }

    @PostMapping("/edit/{postId}")
    public boolean editPost(@RequestBody PostDTO postDTO, @PathVariable Long postId) throws Exception {
        this.postService.editPost(postDTO,postId);
        return true;
    }

    @PostMapping("/delete/{postId}")
    public boolean deletePost(@PathVariable Long postId) throws Exception {
        this.postService.deletePost(postId);
        return true;
    }


    @GetMapping("/totalLikes/{postId}")
    public int getTotalLikesOfPost(@PathVariable Long postId) throws Exception {
        return this.postService.totalLikesOfPost(postId);
    }

    @GetMapping("/followingPosts")
    public List<PostDTO> followingPosts(@RequestBody Long userId){
        return this.postService.getPostsByFollowedUsers(userId);
    }

    @PostMapping("likePost/{postId}")
    public boolean likePost(@PathVariable Long postId){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long loggedInUserId = userService.getUserIdByEmail(userEmail);
        return userService.likePost(loggedInUserId, postId);
    }

    @PostMapping("unlikePost/{postId}")
    public boolean unlikePost(@PathVariable Long postId){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long loggedInUserId = userService.getUserIdByEmail(userEmail);
        return userService.unlikePost(loggedInUserId, postId);
    }
}
