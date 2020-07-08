package fu.rms.service;

import java.util.List;

import fu.rms.dto.CategoryDto;

public interface ICategoryService {

	public List<CategoryDto> getAll();
	
	public CategoryDto getById(Long id);
	
	public CategoryDto create(CategoryDto categoryDto);
	
	public CategoryDto update(CategoryDto categoryDto, Long id);
	
	public void delete(Long id);
}
