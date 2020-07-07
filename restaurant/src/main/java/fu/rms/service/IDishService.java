package fu.rms.service;

import java.util.List;

import fu.rms.dto.DishDto;

public interface IDishService {

	List<DishDto> getAll();
	
	DishDto getById(Long id);	
	
	List<DishDto> getByCategoryId(Long categoryId);
	
	DishDto save(DishDto dishDto);
	
}
