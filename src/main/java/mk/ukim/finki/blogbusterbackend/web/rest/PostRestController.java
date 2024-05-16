package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
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
    public String addPost(@RequestBody PostDTO postDto) throws Exception {
        this.postService.addPost(postDto);
        return "redirect:/api/posts";
    }

    @PostMapping("/edit/{postId}")
    public String editPost(@RequestBody PostDTO postDTO, @PathVariable Long postId) throws Exception {
        this.postService.editPost(postDTO,postId);
        return "redirect:/api/posts";
    }

    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId) throws Exception {
        this.postService.deletePost(postId);
        return "redirect:/api/posts";
    }


    @GetMapping("/totalLikes/{postId}")
    public int getTotalLikesOfPost(@PathVariable Long postId) throws Exception {
        return this.postService.totalLikesOfPost(postId);
    }
}
