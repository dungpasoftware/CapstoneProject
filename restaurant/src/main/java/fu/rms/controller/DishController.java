package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.DishDto;
import fu.rms.service.IDishService;
import fu.rms.service.impl.DishService;

@RestController
@RequestMapping(value = "",produces = "application/json;charset=UTF-8")
public class DishController {

	@Autowired
	private IDishService dishService;
	
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
