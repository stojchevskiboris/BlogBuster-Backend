package mk.ukim.finki.blogbusterbackend.repository;

import mk.ukim.finki.blogbusterbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}