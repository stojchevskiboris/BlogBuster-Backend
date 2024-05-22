package mk.ukim.finki.blogbusterbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.blogbusterbackend.model.Image;
import mk.ukim.finki.blogbusterbackend.model.Post;
import mk.ukim.finki.blogbusterbackend.model.User;
import mk.ukim.finki.blogbusterbackend.model.dto.ImageDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.model.exceptions.ImageNotFoundException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.InvalidPostIdException;
import mk.ukim.finki.blogbusterbackend.model.exceptions.UserNotFoundException;
import mk.ukim.finki.blogbusterbackend.model.mappers.ImageMapper;
import mk.ukim.finki.blogbusterbackend.repository.ImageRepository;
import mk.ukim.finki.blogbusterbackend.repository.PostRepository;
import mk.ukim.finki.blogbusterbackend.repository.UserRepository;
import mk.ukim.finki.blogbusterbackend.service.ImageService;
import mk.ukim.finki.blogbusterbackend.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public List<ImageDTO> getAllImages() {
        return ImageMapper.MapToListViewModel(imageRepository.findAll());
    }

    @Override
    public List<ImageDTO> getImageByPostId(Long id) {
        return ImageMapper.MapToListViewModel(imageRepository.findImagesByPostId(id));
    }

    @Override
    @Transactional
    public boolean addImage(ImageDTO imageDTO) throws Exception {
        Optional<Post> post = postRepository.findById(imageDTO.getPostId());
        if (post.isEmpty()) {
            throw new Exception("Post not found");
        }
        Optional<User> user = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if (user.isEmpty()) {
            throw new Exception("User not found");
        }
        Image image=new Image(imageDTO.getPicture(),user.get(),post.get());
        this.imageRepository.save(image);
        Post p1=post.get();
        p1.setImage(image);
        this.postRepository.save(p1);
        return true;
    }

    @Override
    @Transactional
    public boolean editImage(ImageDTO imageDTO) throws Exception {
        Image image=this.imageRepository.findById(imageDTO.getId()).orElseThrow(ImageNotFoundException::new);
        Optional<User> user = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if(!image.getAuthor().getEmail().equals(user.get().getEmail()))
        {
            throw new Exception("Image not allowed to change");
        }
        image.setPicture(imageDTO.getPicture());
        imageRepository.save(image);
        Post post=postRepository.findById(imageDTO.getPostId()).orElseThrow(InvalidPostIdException::new);
        post.setImage(image);
        postRepository.save(post);
        return true;
    }

    @Override
    @Transactional
    public void deleteImage(Long imageId) throws Exception {
        Image image=this.imageRepository.findById(imageId).orElseThrow(ImageNotFoundException::new);
        Optional<User> user = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if (!image.getAuthor().getEmail().equals(user.get().getEmail())) {
            throw new Exception("Image not allowed to change");
        }
        imageRepository.deleteById(imageId);
    }

    @PostMapping("/addMultipart")
    public Long addPost(@RequestParam("file") MultipartFile file,
                                     @RequestParam("title") String title,
                                     @RequestParam("categoryName") String categoryName,
                                     @RequestParam("content") String content,
                                     @RequestParam("userId") String userId) throws IOException {


        Optional<User> user = userRepository.findByEmail(UserUtils.getLoggedUserEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException(" ");
        }
        var imageBytes = file.getBytes();

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(user.get());
        postRepository.save(post);

        Image image = new Image(imageBytes, user.get(), post);
        imageRepository.save(image);
        post.setImage(image);

        return post.getId();
    }
}
