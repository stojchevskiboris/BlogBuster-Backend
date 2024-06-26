package mk.ukim.finki.blogbusterbackend.model.mappers;

import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.dto.CommentDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;

import java.util.ArrayList;
import java.util.List;

public class PostMapper {
    public static PostDTO MapToViewModel(Post post){
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getUsername(),
                post.getAuthor().getFirstname(),
                post.getAuthor().getLastname(),
                post.getCategory() !=null ? post.getCategory().getName() : null,
                post.getImage() !=null ? post.getImage().getId(): null,
                post.getCreation_date(),
                post.getModified_date(),
                post.getIsModified(),
                post.getAuthor().getId()
        );
    }
    public static List<PostDTO> MapToListViewModel(List<Post> posts){
        List<PostDTO> postsVM = new ArrayList<>();
        for (var p : posts){
            postsVM.add(MapToViewModel(p));
        }
        return postsVM;
    }
}