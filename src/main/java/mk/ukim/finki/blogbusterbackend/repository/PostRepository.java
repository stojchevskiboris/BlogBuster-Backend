package mk.ukim.finki.blogbusterbackend.repository;

import mk.ukim.finki.blogbusterbackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}