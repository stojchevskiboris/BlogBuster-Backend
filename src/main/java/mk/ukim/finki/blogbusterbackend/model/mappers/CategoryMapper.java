package mk.ukim.finki.blogbusterbackend.model.mappers;

import mk.ukim.finki.blogbusterbackend.model.Category;
import mk.ukim.finki.blogbusterbackend.model.Comment;
import mk.ukim.finki.blogbusterbackend.model.dto.CategoryDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.CommentDTO;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public static CategoryDTO MapToViewModel(Category category)
    {
        return new CategoryDTO(
                category.getId(),
                category.getName());
    }
    public static List<CategoryDTO> MapToListViewModel(List<Category> categories){
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (var cat : categories){
            categoryDTOS.add(MapToViewModel(cat));
        }
        return categoryDTOS;
    }
}
