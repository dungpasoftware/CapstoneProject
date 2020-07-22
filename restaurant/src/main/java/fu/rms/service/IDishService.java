package fu.rms.service;

import java.util.List;

import fu.rms.dto.DishDto;
import fu.rms.request.DishRequest;

public interface IDishService {

	List<DishDto> getAll();
	
	DishDto getById(Long id);	
	
	List<DishDto> getByCategoryId(Long categoryId);
	
	DishDto create(DishRequest dishRequest);
	
	DishDto update(DishRequest dishRequest, Long id);
	
	void delete(Long[] ids);
	
	List<DishDto> search(String dishName);
}
