package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.DishDto;
import fu.rms.service.impl.DishService;

@RestController
public class DishController {

	@Autowired
	private DishService dishService;
	
	@GetMapping("/dishes/{dishId}")
	public DishDto dishById(@PathVariable Long dishId) {
		return dishService.getById(dishId);
	}
	
	@GetMapping("/dishes")
	public List<DishDto> listOfDish() {
		return dishService.getAll();
	}
	
	@GetMapping("categories/{categoryId}/dishes")
	public List<DishDto> listOfDishByCategoryId(@PathVariable Long categoryId) {
		return dishService.getByCategoryId(categoryId);
	}
	
}
