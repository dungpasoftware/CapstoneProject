package fu.rms.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import fu.rms.dto.DishDto;

public interface IDishService {

	List<DishDto> getAll();
	
	DishDto getById(Long id);	
	
	List<DishDto> getByCategoryId(Long categoryId);
	
	DishDto create(DishDto dishDto);
	
	DishDto update(DishDto dishDto, Long id);
	
	void delete(Long[] ids);
	
	List<DishDto> search(String dishName);
}
