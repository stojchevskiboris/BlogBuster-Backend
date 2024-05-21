package mk.ukim.finki.blogbusterbackend.repository;


import mk.ukim.finki.blogbusterbackend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findImagesByPostId(Long postId);
}
