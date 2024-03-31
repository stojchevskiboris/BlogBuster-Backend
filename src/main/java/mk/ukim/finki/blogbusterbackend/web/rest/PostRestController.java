package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("details")
    public PostDTO getPostById(@RequestBody Long id) {
        return postService.getPostById(id);
    }

    @PostMapping("add")
    public boolean addPost(@RequestBody PostDTO postDto) throws Exception {
        return this.postService.addPost(postDto);
    }
}
