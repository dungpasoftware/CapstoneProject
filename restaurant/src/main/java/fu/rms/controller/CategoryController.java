package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.CategoryDto;
import fu.rms.service.ICategoryService;

@RestController
@RequestMapping(value = "",produces = "application/json;charset=UTF-8")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping("/categories")
	public List<CategoryDto> all() {
		return categoryService.getAll();
	}

	@GetMapping("/categories/{id}")
	public CategoryDto one(@PathVariable Long id) {
		return categoryService.getById(id);
	}

	@PostMapping("/categories")
	public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
		return categoryService.create(categoryDto);
	}
	
	@PutMapping("/categories/{id}")
	public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long id) {
		return categoryService.update(categoryDto, id);
	}

	@DeleteMapping("/categories/{id}")
	public void deleteCategory(@PathVariable Long id) {
		categoryService.delete(id);
	}

}
