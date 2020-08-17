package fu.rms.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import fu.rms.dto.DishDto;
import fu.rms.request.DishRequest;
import fu.rms.respone.SearchRespone;

@Validated
public interface IDishService {

	List<DishDto> getAll();
	
	DishDto getById(Long id);	
	
	List<DishDto> getByCategoryId(Long categoryId);
	
	DishDto create(@Valid DishRequest dishRequest);
	
	DishDto update(@Valid DishRequest dishRequest, Long id);
	
	void delete(Long[] ids);
	
	SearchRespone<DishDto> search(String dishCode, Long categoryId, Integer page);
}
