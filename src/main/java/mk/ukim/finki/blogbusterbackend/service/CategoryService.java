package mk.ukim.finki.blogbusterbackend.service;

import mk.ukim.finki.blogbusterbackend.model.Category;
import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    List<Post> getPostsByCategoryId(Long id);
    Category getCategoryByName(String name);
    Category addCategory(CategoryDTO categoryDTO);
    Category editCategory(CategoryDTO categoryDTO);
    Category deleteCategory(Long id);
    Category addPostToCategory(Long categoryId, Long postId) throws Exception;
    Category removePostFromCategory(Long categoryId, Long postId) throws Exception;
}
