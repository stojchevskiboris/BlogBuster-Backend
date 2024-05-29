package mk.ukim.finki.blogbusterbackend.web.rest;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.blogbusterbackend.model.Image;
import mk.ukim.finki.blogbusterbackend.model.dto.ImageDTO;
import mk.ukim.finki.blogbusterbackend.service.ImageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "https://blog-buster-fronted.vercel.app/")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping(value = {"","/","list"})
    public List<ImageDTO> listImages()
    {
        return imageService.getAllImages();
    }
    @GetMapping("/{id}")
    public List<ImageDTO> getAllImagesByPostId(@PathVariable Long id)
    {
        return imageService.getImageByPostId(id);
    }
    @PostMapping("/add")
    public boolean add(@RequestBody ImageDTO imageDTO) throws Exception {
        return imageService.addImage(imageDTO);
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody ImageDTO imageDTO) throws Exception {
        return imageService.editImage(imageDTO);
    }
    @PostMapping ("/delete/{id}")
    public String delete(@PathVariable Long id) throws Exception {
        imageService.deleteImage(id);
        return "redirect:/api/images";
    }

}
