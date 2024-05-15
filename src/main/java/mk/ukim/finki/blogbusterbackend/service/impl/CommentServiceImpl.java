package mk.ukim.finki.blogbusterbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.blogbusterbackend.model.Comment;
import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.CommentDTO;
import mk.ukim.finki.blogbusterbackend.model.mappers.CommentMapper;
import mk.ukim.finki.blogbusterbackend.repository.CommentRepository;
import mk.ukim.finki.blogbusterbackend.repository.PostRepository;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;
import mk.ukim.finki.blogbusterbackend.service.CommentService;
import mk.ukim.finki.blogbusterbackend.utils.UserUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return CommentMapper.MapToListViewModel(comments);
    }

    public CommentDTO getCommentById(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.map(CommentMapper::MapToViewModel).orElse(null);
    }

    public List<CommentDTO> getAllCommentsByUserId(Long userId) {
        List<Comment> comments = commentRepository.findCommentsByAuthorId(userId);
        return CommentMapper.MapToListViewModel(comments);
    }

    public List<CommentDTO> getAllCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        return CommentMapper.MapToListViewModel(comments);
    }

    @Transactional
    public boolean addComment(CommentDTO commentDto) throws Exception {
        Optional<Post> post = postRepository.findById(commentDto.getPostId());
        if (post.isEmpty()) {
            throw new Exception("Post not found");
        }
        Optional<User> user = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if (user.isEmpty()) {
            throw new Exception("User not found");
        }
        Comment comment = new Comment(
                commentDto.getContent(),
                user.get(),
                post.get()
        );

        comment.setComment_date(LocalDateTime.now());
        commentRepository.save(comment);
        return true;
    }

    @Transactional
    public boolean editComment(CommentDTO commentDto) throws Exception {
        Optional<Comment> comment = commentRepository.findById(commentDto.getId());
        Optional<User> user = userRepository.findByEmail(UserUtils.getLoggedUserEmail());

        if (comment.isEmpty()) {
            throw new Exception("Comment not found");
        }

        if (!comment.get().getAuthor().getEmail().equals(user.get().getEmail())) {
            throw new Exception("Comment not allowed to change");
        }
        comment.get().setContent(commentDto.getContent());
        commentRepository.save(comment.get());
        return true;
    }

    @Transactional
    public void deleteComment(Long commentId) throws Exception {
        Optional<Comment> comment = commentRepository.findById(commentId);
        Optional<User> user = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if (comment.isEmpty()) {
            throw new Exception("Comment not found");
        }

        if (!comment.get().getAuthor().getEmail().equals(user.get().getEmail())) {
            throw new Exception("Comment not allowed to change");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public int totalRepliesOfComment(Long commentId) throws Exception {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new Exception("Comment not found"));
        return comment.getReplies().size();
    }
}