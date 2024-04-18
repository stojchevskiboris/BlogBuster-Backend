package mk.ukim.finki.blogbusterbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.blogbusterbackend.model.Category;
import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.dto.CategoryDTO;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidCategoryIdException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidCategoryNameException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidPostIdException;
import mk.ukim.finki.blogbusterbackend.repository.CategoryRepository;
import mk.ukim.finki.blogbusterbackend.repository.PostRepository;
import mk.ukim.finki.blogbusterbackend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.findById(id).orElseThrow(InvalidCategoryIdException::new);
    }

    @Override
    public List<Post> getPostsByCategoryId(Long id) {
        return this.categoryRepository.findById(id).orElseThrow(InvalidCategoryIdException::new).getPosts();
    }

    @Override
    public Category getCategoryByName(String name) {
        return this.categoryRepository.findCategoryByName(name).orElseThrow(InvalidCategoryNameException::new);
    }

    @Transactional
    @Override
    public Category addCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getName());
        return this.categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category editCategory(CategoryDTO categoryDTO) {
        Category category = getCategoryById(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setFollowers(categoryDTO.getFollowers());
        category.setPosts(categoryDTO.getPosts());
        return this.categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category deleteCategory(Long id) {
        Category category = getCategoryById(id);
        this.categoryRepository.delete(category);
        return category;
    }

    @Transactional
    @Override
    public Category addPostToCategory(Long categoryId, Long postId) throws Exception {
        if (categoryId == null || postId == null) {
            throw new Exception("Category ID and Post ID must not be null");
        }
        Category category = getCategoryById(categoryId);
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
        Category category = getCategoryById(categoryId);
        Post post = this.postRepository.findById(postId).orElseThrow(InvalidPostIdException::new);
        if (post.getCategory().equals(category)) {
            post.setCategory(null);
            category.getPosts().remove(post);
            this.categoryRepository.save(category);
            this.postRepository.save(post);
        }
        return category;
    }
}
