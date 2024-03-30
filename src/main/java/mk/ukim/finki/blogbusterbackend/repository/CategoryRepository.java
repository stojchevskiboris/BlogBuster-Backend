package mk.ukim.finki.blogbusterbackend.repository;

import mk.ukim.finki.blogbusterbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}