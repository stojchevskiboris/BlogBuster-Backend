package mk.ukim.finki.blogbusterbackend.service;

import mk.ukim.finki.blogbusterbackend.model.Comment;
import mk.ukim.finki.blogbusterbackend.model.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getAllComments();
    CommentDTO getCommentById(Long commentId);
    List<CommentDTO> getAllCommentsByUserId(Long userId);
    List<CommentDTO> getAllCommentsByPostId(Long postId);
    boolean addComment(CommentDTO commentDto) throws Exception;
    boolean editComment(CommentDTO commentDto) throws Exception;
    void deleteComment(Long commentId) throws Exception;
}
