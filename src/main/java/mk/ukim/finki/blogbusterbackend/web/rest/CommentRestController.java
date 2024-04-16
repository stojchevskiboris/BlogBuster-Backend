package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.model.Comment;
import mk.ukim.finki.blogbusterbackend.model.dto.CommentDTO;
import mk.ukim.finki.blogbusterbackend.service.impl.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/comments")
public class CommentRestController {
    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = {"", "/", "/list"})
    public List<CommentDTO> listPosts() {
        return commentService.getAllComments();
    }

    @GetMapping("/{postId}")
    public List<CommentDTO> getAllCommentsByPostId(@PathVariable Long postId) {
        return commentService.getAllCommentsByPostId(postId);
    }

    @PostMapping("/add")
    public boolean addComment(@RequestBody CommentDTO commentDto) throws Exception {
        return commentService.addComment(commentDto);
    }

    @PutMapping("/edit")
    public boolean editComment(@RequestBody CommentDTO commentDto) throws Exception {
        return commentService.editComment(commentDto);
    }

    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable Long commentId) throws Exception {
        this.commentService.deleteComment(commentId);
        return "redirect:/api/comments";
    }
}
