package mk.ukim.finki.blogbusterbackend.service;

import jakarta.transaction.Transactional;
import mk.ukim.finki.blogbusterbackend.model.Comment;
import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.CommentDTO;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidCommentIdException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidPostIdException;
import mk.ukim.finki.blogbusterbackend.model.mappers.CommentMapper;
import mk.ukim.finki.blogbusterbackend.repository.CommentRepository;
import mk.ukim.finki.blogbusterbackend.repository.PostRepository;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<CommentDTO> getAllComments(){
        List<Comment> comments = this.commentRepository.findAll();
        return CommentMapper.MapToListViewModel(comments);
    }

    public CommentDTO geCommentById(Long commentId) {
        Optional<Comment> comment = this.commentRepository.findById(commentId);
        return comment.map(CommentMapper::MapToViewModel).orElse(null);
    }

    public List<CommentDTO> getAllByPostId(Long postId){
        List<Comment> comments = this.commentRepository.findCommentsByPostId(postId);
        return CommentMapper.MapToListViewModel(comments);
    }

    public List<CommentDTO> getAllByUserId(Long authorId){
        List<Comment> comments = this.commentRepository.findCommentsByAuthorId(authorId);
        return CommentMapper.MapToListViewModel(comments);
    }


    @Transactional
    public boolean addComment(CommentDTO commentDTO) throws Exception {
        Optional<User> user = userRepository.findByUsername(commentDTO.getAuthorUsername());
        if (user.isEmpty()){
            throw new Exception("User not existing");
        }
        Optional<Post> post = postRepository.findById(commentDTO.getPostId());
        if (post.isEmpty()){
            throw new InvalidPostIdException();
        }

        Comment comment = new Comment(
                commentDTO.getContent(),
                user.get(),
                post.get()
        );

        commentRepository.save(comment);
        return true;
    }

    @Transactional
    public boolean editComment(CommentDTO commentDTO) {
        Optional<Post> post = this.postRepository.findById(commentDTO.getPostId());
        if (post.isEmpty()){
            throw new InvalidPostIdException();
        }

        Optional<Comment> comment = commentRepository.findById(commentDTO.getId());
        if (comment.isEmpty()){
            throw new InvalidCommentIdException();
        }

        comment.get().setContent(commentDTO.getContent());
        commentRepository.save(comment.get());
        return true;
    }

    @Transactional
    public boolean deleteComment(Long commentId) throws Exception {
        Optional<Comment> comment = this.commentRepository.findById(commentId);
        if (comment.isEmpty()){
            throw new InvalidCommentIdException();
        }
        commentRepository.deleteById(commentId);
        return true;
    }

}
