package mk.ukim.finki.blogbusterbackend.model.mappers;

import mk.ukim.finki.blogbusterbackend.model.Comment;
import mk.ukim.finki.blogbusterbackend.model.dto.CommentDTO;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    public static CommentDTO MapToViewModel(Comment comment){
        return new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getUsername(),
                comment.getPost().getId(),
                comment.getComment_date(),
                comment.getAuthor().getFirstname(),
                comment.getAuthor().getLastname()
        );
    }

    public static List<CommentDTO> MapToListViewModel(List<Comment> comments){
        List<CommentDTO> commentsVM = new ArrayList<>();
        for (var c : comments){
            commentsVM.add(MapToViewModel(c));
        }
        return commentsVM;
    }

}
