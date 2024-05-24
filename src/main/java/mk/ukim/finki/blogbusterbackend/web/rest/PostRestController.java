package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.AddPostDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.FilterDTO;
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
    public boolean addPost(@RequestBody AddPostDTO postDto) throws Exception {
        this.postService.addPost(postDto);
        return true;
    }

    @PostMapping("/edit/{postId}")
    public boolean editPost(@RequestBody AddPostDTO data) throws Exception {
        this.postService.editPost(data);
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

    @GetMapping("/followingPosts/{userId}")
    public List<PostDTO> followingPosts(@PathVariable Long userId){
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

    @GetMapping("getLikedPosts")
    public List<Long> getLikedPosts(){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Long loggedInUserId = userService.getUserIdByEmail(userEmail);
        return userService.getLikedPosts(loggedInUserId);
    }

    @PostMapping("/filterPosts")
    public List<PostDTO> filterPosts(@RequestBody FilterDTO filterDTO)
    {
        return this.postService.filterPosts(filterDTO);
    }

    @GetMapping("/viewPosts/{id}")
    public List<PostDTO> getPostsFromUserProfile(@PathVariable Long id)
    {
        return this.postService.getPostsFromUserProfile(id);
    }
}
