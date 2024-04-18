package mk.ukim.finki.blogbusterbackend.service.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.blogbusterbackend.model.Category;
import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.model.mappers.PostMapper;
import mk.ukim.finki.blogbusterbackend.repository.CategoryRepository;
import mk.ukim.finki.blogbusterbackend.repository.PostRepository;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;
import mk.ukim.finki.blogbusterbackend.service.PostService;
import mk.ukim.finki.blogbusterbackend.utils.UserUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = this.postRepository.findAll();
        return PostMapper.MapToListViewModel(posts);
    }

    public PostDTO getPostById(Long postId) {
        Optional<Post> post = this.postRepository.findById(postId);
        return post.map(PostMapper::MapToViewModel).orElse(null);
    }

    public List<PostDTO> getAllByUserId(Long authorId) {
        List<Post> posts = this.postRepository.findPostsByAuthorId(authorId);
        return PostMapper.MapToListViewModel(posts);
    }


    @Transactional
    public Optional<Post> addPost(PostDTO postDto) throws Exception {
        Optional<User> user = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if (user.isEmpty()) {
            throw new Exception("User not existing");
        }
        Optional<Category> category;
        if (postDto.getCategoryName() == null || postDto.getCategoryName().isEmpty()) {
            category = Optional.empty();
        } else {
            category = categoryRepository.findCategoryByName(postDto.getCategoryName());
        }
        Post post = new Post(
                postDto.getTitle(),
                postDto.getContent(),
                user.get(),
                category.orElse(null),
                postDto.getImage()
        );

        post.setCreation_date(LocalDate.now());

        postRepository.save(post);
        return Optional.of(post);
    }

    @Transactional
    public Optional<Post> editPost(PostDTO postDto, Long postId) throws Exception {
        Optional<Post> post = this.postRepository.findById(postId);
        Optional<User> user = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if (post.isEmpty()) {
            throw new Exception("Post not existing");
        }

        if (!post.get().getAuthor().getEmail().equals(user.get().getEmail())) {
            throw new Exception("Post not allowed to change");
        }

        post.get().setTitle(postDto.getTitle());
        post.get().setContent(postDto.getContent());
        postRepository.save(post.get());
        return post;
    }

    @Transactional
    public void deletePost(Long postId) throws Exception {
        Optional<Post> post = this.postRepository.findById(postId);
        Optional<User> user = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if (post.isEmpty()) {
            throw new Exception("Post not existing");
        }

        if (!post.get().getAuthor().getEmail().equals(user.get().getEmail())) {
            throw new Exception("Post not allowed to change");
        }
        postRepository.deleteById(postId);
    }

}