package mk.ukim.finki.blogbusterbackend.service.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.blogbusterbackend.model.*;
import mk.ukim.finki.blogbusterbackend.model.dto.AddPostDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.FilterDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.model.exceptions.ImageNotFoundException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.blogbusterbackend.model.mappers.PostMapper;
import mk.ukim.finki.blogbusterbackend.repository.*;
import mk.ukim.finki.blogbusterbackend.service.PostService;
import mk.ukim.finki.blogbusterbackend.utils.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ReplyRepository replyRepository, UserRepository userRepository, CategoryRepository categoryRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.replyRepository = replyRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    public List<PostDTO> getAllPosts() {
        // List<Post> posts = this.postRepository.findAll();
        List<Post> posts = getPostByFollowedUsersImpl();
        // Sort the posts by modified date in descending order
//        posts.sort(Comparator.comparing(Post::getModified_date).reversed());
        posts.sort(Comparator.comparing(
                        Post::getModified_date, Comparator.nullsLast(Comparator.reverseOrder()))
                .thenComparing(Post::getCreation_date, Comparator.nullsLast(Comparator.reverseOrder())));
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
    public Optional<Post> addPost(AddPostDTO data) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if (userOptional.isEmpty()) {
            throw new Exception("User not existing");
        }
        User user = userOptional.get();

        Category category = null;

        if (data.getPostDTO() != null && data.getPostDTO().getCategoryName() != null){
            var optionalCategory = categoryRepository.findCategoryByName(data.getPostDTO().getCategoryName());
            if (optionalCategory.isPresent()){
                category = optionalCategory.get();
            }
            else {
                category = new Category(data.getPostDTO().getCategoryName());
                categoryRepository.save(category);
            }

        }

        Post post = new Post(
                data.getPostDTO().getTitle(),
                data.getPostDTO().getContent(),
                user,
                category,
                null
        );

        post.setCreation_date(LocalDateTime.now());
        post.setModified_date(LocalDateTime.now());

        postRepository.save(post);
        if (data.getImage()!=null){
            Image image = new Image(data.getImage().getBytes(), user, post);
            imageRepository.save(image);
            post.setImage(image);
            postRepository.save(post);
        }
        return Optional.of(post);
    }

    @Transactional
    public PostDTO editPost(AddPostDTO data) throws Exception {
        Optional<Post> optionalPost = this.postRepository.findById(data.getPostDTO().getId());
        Optional<User> user = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if (optionalPost.isEmpty()) {
            throw new Exception("Post not existing");
        }

        Post post = optionalPost.get();

        if (!post.getAuthor().getEmail().equals(user.get().getEmail())) {
            throw new Exception("Post not allowed to change");
        }

        post.setTitle(data.getPostDTO().getTitle());
        post.setContent(data.getPostDTO().getContent());
        post.setIsModified(true);
        post.setModified_date(LocalDateTime.now());


        if (data.getPostDTO() != null && data.getPostDTO().getCategoryName() != null){
            Category category = null;
            var optionalCategory = categoryRepository.findCategoryByName(data.getPostDTO().getCategoryName());
            if (optionalCategory.isPresent()){
                category = optionalCategory.get();
            }
            else {
                category = new Category(data.getPostDTO().getCategoryName());
                categoryRepository.save(category);
            }
            post.setCategory(category);
        }



        if (data.getImage()!=null){
            Image image = new Image(data.getImage().getBytes(), user.get(), post);
            imageRepository.save(image);
            post.setImage(image);
            postRepository.save(post);
        }

        postRepository.save(post);
        return PostMapper.MapToViewModel(post);
    }

    @Transactional
    public void deletePost(Long postId) throws Exception {
        Optional<Post> post = this.postRepository.findById(postId);
        Optional<User> user = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if (post.isEmpty()) {
            throw new Exception("Post not existing");
        }

        if(user.isEmpty()){
            throw new InvalidUserIdException();
        }

        if (!post.get().getAuthor().getEmail().equals(user.get().getEmail())) {
            throw new Exception("Post not allowed to change");
        }

        List<User> users = userRepository.findAll();
        for (User u : users) {
            u.getLikedPosts().removeIf(p -> p.getId().equals(postId));
            userRepository.save(u);
        }

        postRepository.deleteById(postId);
    }

    //method for converting Date object to String ---> used for the search part
    public String convertDateToString(LocalDateTime creationDate) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        return dateTimeFormat.format(creationDate);
    }


    @Transactional
    public List<PostDTO> filterPosts(FilterDTO filterDTO) {
        //List<Post> posts = postRepository.findAll();
        List<Post> posts = getPostByFollowedUsersImpl();
        posts = posts.stream()
                .filter(p -> (filterDTO.getCategoryId() == null || p.getCategory().getId().equals(filterDTO.getCategoryId())))
                .filter(p -> (filterDTO.getAuthorUsername() == null || filterDTO.getAuthorUsername().isEmpty() || p.getAuthor().getUsername().toLowerCase().contains(filterDTO.getAuthorUsername().toLowerCase())))
                .filter(p -> (filterDTO.getTitle() == null || filterDTO.getTitle().isEmpty() || p.getTitle().toLowerCase().contains(filterDTO.getTitle().toLowerCase()) ||
                        p.getContent().toLowerCase().contains(filterDTO.getTitle().toLowerCase())))
                .collect(Collectors.toList());
        return PostMapper.MapToListViewModel(posts);
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

    @Override // prikaz na postovi koga korsinikot ke poseti dr profil
    public List<PostDTO> getPostsFromUserProfile(Long userId) {
        return PostMapper.MapToListViewModel(getPostByFollowedUsersImpl());
    }

    private List<Post> getPostByFollowedUsersImpl(){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByEmail(userEmail).orElseThrow(InvalidUserIdException::new);
        if (user == null) {
            throw new InvalidUserIdException();
        }
        List<User> followingUsers = user.getFollowingUsers();
        followingUsers.add(user); // i postovite od samiot user treba da se gledaat
        return followingUsers.stream()
                .flatMap(followingUser -> postRepository.findPostsByAuthorId(followingUser.getId()).stream())
                .collect(Collectors.toList());
    }
}