package mk.ukim.finki.blogbusterbackend.service.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.blogbusterbackend.model.Category;
import mk.ukim.finki.blogbusterbackend.model.Image;
import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.FilterDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.model.exceptions.ImageNotFoundException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.blogbusterbackend.model.mappers.PostMapper;
import mk.ukim.finki.blogbusterbackend.repository.CategoryRepository;
import mk.ukim.finki.blogbusterbackend.repository.ImageRepository;
import mk.ukim.finki.blogbusterbackend.repository.PostRepository;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;
import mk.ukim.finki.blogbusterbackend.service.PostService;
import mk.ukim.finki.blogbusterbackend.utils.UserUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
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
        Optional<User> userOptional = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if (userOptional.isEmpty()) {
            throw new Exception("User not existing");
        }
        User user = userOptional.get();

        Optional<Category> categoryOptional = (postDto.getCategoryName() == null || postDto.getCategoryName().isEmpty())
                ? Optional.empty()
                : categoryRepository.findCategoryByName(postDto.getCategoryName());

        Category category = categoryOptional.orElse(null);
        Image image=imageRepository.findById(postDto.getImageId()).orElseThrow(ImageNotFoundException::new);
        Post post = new Post(
                postDto.getTitle(),
                postDto.getContent(),
                user,
                category,
                image
        );

        post.setCreation_date(LocalDateTime.now());
        post.setModified_date(LocalDateTime.now());

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
        Image image=imageRepository.findById(postDto.getImageId()).orElseThrow(ImageNotFoundException::new);

        post.get().setTitle(postDto.getTitle());
        post.get().setContent(postDto.getContent());
        post.get().setImage(image);
        post.get().setIsModified(true);
        post.get().setModified_date(LocalDateTime.now());
        postRepository.save(post.get());
        image.setPost(post.get());
        image.setAuthor(user.get());
        imageRepository.save(image);
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

    //method for converting Date object to String ---> used for the search part
    public String convertDateToString(LocalDateTime creationDate)
    {
        DateTimeFormatter dateTimeFormat=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTimeFormat.format(creationDate);
    }
    @Transactional
    public List<Post> filterPosts(FilterDTO filterDTO) {
        List<Post> posts = postRepository.findAll();
        return posts.stream().
                filter(p -> (filterDTO.getCategoryId() == null || p.getCategory().getId().equals(filterDTO.getCategoryId())))
                .filter(p -> (filterDTO.getAuthorUsername() == null || p.getAuthor().getUsername().equalsIgnoreCase(filterDTO.getAuthorUsername())))
                .filter(p -> (filterDTO.getFrom() == null || p.getCreation_date().isAfter(filterDTO.getFrom())))
                .filter(p -> (filterDTO.getTo() == null || p.getCreation_date().isBefore(filterDTO.getTo())))
                .filter(p -> (filterDTO.getTitle() == null || p.getTitle().equalsIgnoreCase(filterDTO.getTitle())))
                .filter(p -> (filterDTO.getContext() == null ||
                        p.getContent().contains(filterDTO.getContext()) ||
                        p.getAuthor().getUsername().contains(filterDTO.getContext()) ||
                        p.getTitle().contains(filterDTO.getContext())) ||
                        convertDateToString(p.getCreation_date()).contains(filterDTO.getContext()))
                .collect(Collectors.toList());
    }

    @Override
    public int totalLikesOfPost(Long postId) throws Exception {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            return post.getLikedByUsers().size();
        } else {
            throw new Exception("Post not existing");
        }
    }

    @Override
    public List<PostDTO> getPostsByFollowedUsers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(InvalidUserIdException::new);
        List<User> followingUsers = user.getFollowingUsers();
        List<Post> postsOfFollowingUsers = followingUsers.stream()
                .flatMap(followingUser -> postRepository.findPostsByAuthorId(followingUser.getId()).stream())
                .collect(Collectors.toList());

        return PostMapper.MapToListViewModel(postsOfFollowingUsers);
    }


}