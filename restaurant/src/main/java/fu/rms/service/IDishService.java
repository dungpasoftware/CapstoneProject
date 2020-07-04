package fu.rms.service;

import java.util.List;

import fu.rms.dto.DishDto;

public interface IDishService {

	DishDto getById(Long id);	
	
	List<DishDto> getByCategoryId(Long categoryId);
	
	DishDto updateRemainQuantity(Long dishId, int quantity);
}
