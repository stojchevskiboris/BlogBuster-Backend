package mk.ukim.finki.blogbusterbackend.service;

import mk.ukim.finki.blogbusterbackend.model.Category;
import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.CategoryDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    List<PostDTO> getPostsByCategoryId(Long id);
    CategoryDTO getCategoryByName(String name);
    Category addCategory(CategoryDTO categoryDTO);
    Category editCategory(CategoryDTO categoryDTO, Long categoryId);
    Category deleteCategory(Long id);
    Category addPostToCategory(Long categoryId, Long postId) throws Exception;
    Category removePostFromCategory(Long categoryId, Long postId) throws Exception;
}
