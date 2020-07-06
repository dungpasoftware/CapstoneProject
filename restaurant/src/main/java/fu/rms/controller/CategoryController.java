package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.CategoryDto;
import fu.rms.service.ICategoryService;

@RestController
public class CategoryController {

	@Autowired
	private ICategoryService iCategoryService;

	@GetMapping("/categories")
	public List<CategoryDto> listOfCategory() {
		return iCategoryService.getAll();
	}

	@GetMapping("/categories/{id}")
	public CategoryDto categoryById(@PathVariable Long id) {
		return iCategoryService.getById(id);
	}

}
