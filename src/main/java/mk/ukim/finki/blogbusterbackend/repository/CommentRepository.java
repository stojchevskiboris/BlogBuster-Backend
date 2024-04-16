package mk.ukim.finki.blogbusterbackend.repository;


import mk.ukim.finki.blogbusterbackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByAuthorId(Long userId);

    List<Comment> findCommentsByPostId(Long postId);
}
