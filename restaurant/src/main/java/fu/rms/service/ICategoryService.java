package fu.rms.service;

import java.util.List;

import fu.rms.dto.CategoryDto;

public interface ICategoryService {

	public List<CategoryDto> getAll();
	
	public CategoryDto getById(Long id, boolean isGetdishes);
}
