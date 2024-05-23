package mk.ukim.finki.blogbusterbackend.model.mappers;

import mk.ukim.finki.blogbusterbackend.model.Comment;
import mk.ukim.finki.blogbusterbackend.model.Image;
import mk.ukim.finki.blogbusterbackend.model.dto.CommentDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.ImageDTO;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageMapper {
    public static ImageDTO MapToViewModel(Image image) {
        return new ImageDTO(
                image.getId(),
                image.getPicture(),
                image.getAuthor().getUsername(),
                image.getPost().getId(),
                new String(image.getPicture(), StandardCharsets.UTF_8)
        );
    }

    public static List<ImageDTO> MapToListViewModel(List<Image> images) {
        List<ImageDTO> imagesVM = new ArrayList<>();
        for (var i : images) {
            imagesVM.add(MapToViewModel(i));
        }
        return imagesVM;
    }
}
