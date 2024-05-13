package mk.ukim.finki.blogbusterbackend.service;

import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.dto.FilterDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<PostDTO> getAllPosts();
    PostDTO getPostById(Long postId);
    List<PostDTO> getAllByUserId(Long authorId);
    Optional<Post> addPost(PostDTO postDto) throws Exception;
    Optional<Post> editPost(PostDTO postDto, Long postId) throws Exception;
    void deletePost(Long postId) throws Exception;
    List<Post> filterPosts(FilterDTO filterDTO);
}
