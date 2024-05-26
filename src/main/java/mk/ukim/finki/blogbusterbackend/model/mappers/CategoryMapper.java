package mk.ukim.finki.blogbusterbackend.model.mappers;

import mk.ukim.finki.blogbusterbackend.model.Category;
import mk.ukim.finki.blogbusterbackend.model.Comment;
import mk.ukim.finki.blogbusterbackend.model.dto.CategoryDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.CommentDTO;
import org.hibernate.mapping.Any;
import org.slf4j.event.KeyValuePair;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public static CategoryDTO MapToViewModel(Category category)
    {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getFollowers(),
                category.getPosts());
    }
    public static List<CategoryDTO> MapToListViewModel(List<Category> categories){
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (var cat : categories){
            categoryDTOS.add(MapToViewModel(cat));
        }
        return categoryDTOS;
    }

    public static KeyValue MapToKeyValue(CategoryDTO dto){
        return new KeyValue(dto.getId(), dto.getName());
    }

    public static List<KeyValue> MapToKeyValueList(List<CategoryDTO> DTOs){
        List<KeyValue> categoryKeyValues = new ArrayList<>();
        for (var cat : DTOs){
            categoryKeyValues.add(MapToKeyValue(cat));
        }
        return categoryKeyValues;
    }

    public static KeyValue MapToKeyValueDomain(Category domainModel){
        return new KeyValue(domainModel.getId(), domainModel.getName());
    }

    public static List<KeyValue> MapToKeyValueDomainList(List<Category> domainList){
        List<KeyValue> categoryKeyValues = new ArrayList<>();
        for (var cat : domainList){
            categoryKeyValues.add(MapToKeyValueDomain(cat));
        }
        return categoryKeyValues;
    }
}