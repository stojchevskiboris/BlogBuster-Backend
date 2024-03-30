package mk.ukim.finki.blogbusterbackend.repository;

import mk.ukim.finki.blogbusterbackend.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

}