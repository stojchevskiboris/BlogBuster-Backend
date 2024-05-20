package mk.ukim.finki.blogbusterbackend.web.rest;

import mk.ukim.finki.blogbusterbackend.model.dto.CategoryDTO;
import mk.ukim.finki.blogbusterbackend.model.dto.PostDTO;
import mk.ukim.finki.blogbusterbackend.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = {" ","/"})
    public List<CategoryDTO> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public CategoryDTO getCategoryById(@PathVariable Long categoryId)
    {
        return this.categoryService.getCategoryById(categoryId);
    }

    @GetMapping("/posts/{categoryId}")
    public List<PostDTO> getPostsByCategoryId(@PathVariable Long categoryId)
    {
        return this.categoryService.getPostsByCategoryId(categoryId);
    }

    @PostMapping("/add")
    public String addCategory(@RequestBody CategoryDTO categoryDTO)
    {
        this.categoryService.addCategory(categoryDTO);
        return "redirect:/api/categories";
    }

    @PostMapping("/edit/{categoryId}")
    public String editCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) throws Exception {
        this.categoryService.editCategory(categoryDTO,categoryId);
        return "redirect:/api/categories";
    }

    @PostMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId) throws Exception {
        this.categoryService.deleteCategory(categoryId);
        return "redirect:/api/categories";
    }

    @GetMapping("/discoverCategories/{userId}")
    public List<CategoryDTO> discoverCategories(@PathVariable Long userId)
    {
        return this.categoryService.discoverCategories(userId);
    }
}
