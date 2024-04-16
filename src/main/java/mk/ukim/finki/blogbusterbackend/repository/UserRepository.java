package mk.ukim.finki.blogbusterbackend.repository;

import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.enumerations.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}