package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.model.dto.CommentDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.service.CommentService;
import mk.ukim.finki.blogbusterbackend.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {
    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = {"","/", "/list"})
    public List<CommentDTO> listComments() {
        return commentService.getAllComments();
    }

    @GetMapping("getByPostId")
    public List<CommentDTO> getCommentsByPostId(@RequestBody Long id) {
        return commentService.getAllByPostId(id);
    }

    @GetMapping("getByUserId")
    public List<CommentDTO> getCommentsByUserId(@RequestBody Long id) {
        return commentService.getAllByUserId(id);
    }

    @GetMapping("getById")
    public CommentDTO getCommentById(@RequestBody Long id) {
        return commentService.geCommentById(id);
    }

    @PostMapping("add")
    public boolean addComment(@RequestBody CommentDTO commentDTO) throws Exception {
        return this.commentService.addComment(commentDTO);
    }

    @PostMapping("edit")
    public boolean editComment(@RequestBody CommentDTO commentDTO) throws Exception {
        return this.commentService.editComment(commentDTO);
    }

    @PostMapping("deleteById")
    public boolean editComment(@RequestBody Long id) throws Exception {
        return this.commentService.deleteComment(id);
    }
}
