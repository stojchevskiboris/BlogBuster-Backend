package mk.ukim.finki.blogbusterbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.blogbusterbackend.model.Category;
import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.CategoryDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidCategoryIdException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidCategoryNameException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidPostIdException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.blogbusterbackend.model.mappers.CategoryMapper;
import mk.ukim.finki.blogbusterbackend.model.mappers.KeyValue;
import mk.ukim.finki.blogbusterbackend.model.mappers.PostMapper;
import mk.ukim.finki.blogbusterbackend.repository.CategoryRepository;
import mk.ukim.finki.blogbusterbackend.repository.PostRepository;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;
import mk.ukim.finki.blogbusterbackend.service.CategoryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return CategoryMapper.MapToListViewModel(this.categoryRepository.findAll());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        return CategoryMapper.MapToViewModel(this.categoryRepository.findById(id).orElseThrow(InvalidCategoryIdException::new));
    }

    @Override
    public List<PostDTO> getPostsByCategoryId(Long categoryId) {
        return PostMapper.MapToListViewModel(postRepository.findPostsByCategoryId(categoryId));
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return CategoryMapper.MapToViewModel(this.categoryRepository.findCategoryByName(name).
                orElseThrow(InvalidCategoryNameException::new));
    }

    @Transactional
    @Override
    public Category addCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getName());
        return this.categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category editCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category =this.categoryRepository.findById(categoryId)
                .orElseThrow(InvalidCategoryIdException::new);
        category.setName(categoryDTO.getName());
        category.setFollowers(categoryDTO.getFollowers());
        category.setPosts(categoryDTO.getPosts());
        return this.categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category deleteCategory(Long id) {
        Category category =this.categoryRepository.findById(id)
                .orElseThrow(InvalidCategoryIdException::new);
        this.categoryRepository.delete(category);
        return category;
    }

    @Transactional
    @Override
    public Category addPostToCategory(Long categoryId, Long postId) throws Exception {
        if (categoryId == null || postId == null) {
            throw new Exception("Category ID and Post ID must not be null");
        }
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(InvalidCategoryIdException::new);
        Post post = this.postRepository.findById(postId).orElseThrow(InvalidPostIdException::new);
        if (!post.getCategory().equals(category)) {
            category.getPosts().add(post);
            post.setCategory(category);
            this.categoryRepository.save(category);
            this.postRepository.save(post);
        }
        return category;
    }

    @Transactional
    @Override
    public Category removePostFromCategory(Long categoryId, Long postId) throws Exception {
        if (categoryId == null || postId == null) {
            throw new Exception("Category ID and Post ID must not be null");
        }
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(InvalidCategoryIdException::new);
        Post post = this.postRepository.findById(postId).orElseThrow(InvalidPostIdException::new);
        if (post.getCategory().equals(category)) {
            post.setCategory(null);
            category.getPosts().remove(post);
            this.categoryRepository.save(category);
            this.postRepository.save(post);
        }
        return category;
    }
    @Override
    public List<CategoryDTO> discoverCategories(Long userId) {
        User currentUser=userRepository.findById(userId).orElseThrow(InvalidUserIdException::new);
        List<Category>categories=this.categoryRepository.findAll().stream()
                .filter(cat->!cat.getFollowers().contains(currentUser))
                .sorted((c1,c2)->Integer.compare(c1.getFollowers().size(),c2.getFollowers().size()))
                //.limit(10)
                .collect(Collectors.toList());
        return CategoryMapper.MapToListViewModel(categories);
    }

    @Override
    public List<KeyValue> getFollowedCategories() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = this.userRepository.findByEmail(userEmail).orElseThrow(InvalidUserIdException::new);

        List<Category>categories=this.categoryRepository.findAll().stream()
                .filter(cat->cat.getFollowers().contains(currentUser))
                .sorted((c1,c2)->Integer.compare(c1.getFollowers().size(),c2.getFollowers().size()))
                //.limit(10)
                .collect(Collectors.toList());
        return CategoryMapper.MapToKeyValueDomainList(categories);
    }
}
