package fu.rms.service;

import java.util.List;

import fu.rms.dto.CategoryDto;
import fu.rms.request.CategoryRequest;

public interface ICategoryService {

	public List<CategoryDto> getAll();
	
	public CategoryDto getById(Long id);
	
	public CategoryDto create(CategoryRequest CategoryRequest);
	
	public CategoryDto update(CategoryRequest CategoryRequest, Long id);
	
	public void delete(Long id);
}
