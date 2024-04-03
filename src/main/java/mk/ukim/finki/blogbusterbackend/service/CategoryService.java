package mk.ukim.finki.blogbusterbackend.service;

import jakarta.transaction.Transactional;
import mk.ukim.finki.blogbusterbackend.model.Category;
import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.CategoryDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.UserDTO;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidCategoryIdException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidPostIdException;
import mk.ukim.finki.blogbusterbackend.model.mappers.CategoryMapper;
import mk.ukim.finki.blogbusterbackend.model.mappers.PostMapper;
import mk.ukim.finki.blogbusterbackend.repository.CategoryRepository;
import mk.ukim.finki.blogbusterbackend.repository.PostRepository;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, PostRepository postRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        return CategoryMapper.MapToListViewModel(categories);
    }

    public CategoryDTO getCategoryById(Long id) {
        return this.categoryRepository.findById(id).
                map(CategoryMapper::MapToViewModel).orElse(null);
    }

    public CategoryDTO getCategoryByName(String name) {
        return this.categoryRepository.findCategoryByName(name).
                map(CategoryMapper::MapToViewModel).orElse(null);
    }

    public List<PostDTO> getPostsByCategoryId(Long categoryId) {
        List<Post> posts = this.postRepository.findPostsByCategoryId(categoryId);
        return PostMapper.MapToListViewModel(posts);
    }
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = new Category(
                categoryDTO.getName()
        );
        categoryRepository.save(category);
    }

    public void editCategory(CategoryDTO categoryDTO) throws Exception {
        Optional<Category> category = this.categoryRepository.findById(categoryDTO.getId());
        if (category.isEmpty()) {
            throw new Exception("Category doesn't exist");
        }
        category.get().setName(categoryDTO.getName());
        categoryRepository.save(category.get());
    }

    public boolean deleteCategory(Long id) throws Exception {
        Optional<Category> category = this.categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new Exception("Category doesn't exist");
        }
        categoryRepository.delete(category.get());
        return true;
    }

    public void addPostToCategory(CategoryDTO categoryDTO, PostDTO postDTO) {
        Category category = this.categoryRepository.findById(categoryDTO.getId()).
                orElseThrow(InvalidCategoryIdException::new);
        Post post = this.postRepository.findById(postDTO.getId())
                .orElseThrow(InvalidPostIdException::new);
        category.getPosts().add(post);
        categoryRepository.save(category);
    }

    public void removePostFromCategory(CategoryDTO categoryDTO, PostDTO postDTO) {
        Category category = this.categoryRepository.findById(categoryDTO.getId()).
                orElseThrow(InvalidCategoryIdException::new);
        Post post = this.postRepository.findById(postDTO.getId())
                .orElseThrow(InvalidPostIdException::new);
        category.getPosts().remove(post);
        categoryRepository.save(category);
    }
}
