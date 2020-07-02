package fu.rms.service;

import java.util.List;

import fu.rms.dto.DishDto;

public interface IDishService {

	DishDto getById(Long id);	
	
	List<DishDto> getAll();
	
	List<DishDto> getByCategoryId(Long categoryId);
}
