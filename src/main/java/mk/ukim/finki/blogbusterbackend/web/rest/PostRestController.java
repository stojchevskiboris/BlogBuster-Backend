package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.service.impl.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = {"","/", "/list"})
    public List<PostDTO> listPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/details")
    public PostDTO getPostById(@RequestBody Long id) {
        return postService.getPostById(id);
    }

    @PostMapping("/add")
    public Optional<Post> addPost(@RequestBody PostDTO postDto) throws Exception {
        return postService.addPost(postDto);

    }

    @PutMapping("/edit/{postId}")
    public Optional<Post> editPost(@RequestBody PostDTO postDTO, @PathVariable Long postId) throws Exception {
        return postService.editPost(postDTO, postId);
    }

    @DeleteMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId) throws Exception {
         this.postService.deletePost(postId);
            return "redirect:/api/posts";
    }
}
