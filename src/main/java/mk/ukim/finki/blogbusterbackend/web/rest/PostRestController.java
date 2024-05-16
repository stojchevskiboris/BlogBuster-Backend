package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostRestController {
    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
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
}
