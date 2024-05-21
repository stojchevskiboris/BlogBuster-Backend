package mk.ukim.finki.blogbusterbackend.service;

import mk.ukim.finki.blogbusterbackend.model.dto.ImageDTO;

import java.util.List;

public interface ImageService {
    List<ImageDTO> getAllImages();
    List<ImageDTO> getImageByPostId(Long id);
     boolean addImage(ImageDTO imageDTO) throws Exception;
     boolean editImage(ImageDTO imageDTO) throws Exception;
     void deleteImage(Long imageId) throws Exception;
}
