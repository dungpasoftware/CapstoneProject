package fu.rms.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.CategoryDto;
import fu.rms.service.ICategoryService;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class CategoryController {

	@Autowired
	private ICategoryService iCategoryService;

	@GetMapping("/categories")
	public List<CategoryDto> listOfCategory() {
		return iCategoryService.getAll();
	}

	@GetMapping("/categories/{id}")
	public CategoryDto categoryById(@PathVariable Long id, @RequestParam Boolean isGetDishes ) {
		return iCategoryService.getById(id,isGetDishes);
	}

}
