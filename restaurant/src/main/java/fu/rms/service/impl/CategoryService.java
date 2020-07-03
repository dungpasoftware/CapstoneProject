package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.CategoryDto;
import fu.rms.dto.DishDto;
import fu.rms.entity.Category;
import fu.rms.exception.NotFoundException;
import fu.rms.mapper.CategoryMapper;
import fu.rms.mapper.DishMapper;
import fu.rms.repository.CategoryRepository;
import fu.rms.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private DishMapper DishMapper;
	
	@Override
	public List<CategoryDto> getAll() {
		
		List<Category> categories=categoryRepo.findAll();
		List<CategoryDto> categoryDtos=categories.stream().map(categoryMapper::entityToDto).collect(Collectors.toList());
		return categoryDtos;
		
	}

	@Override
	public CategoryDto getById(Long id) {
		Category category=categoryRepo.findById(id)
				.orElseThrow(()-> new NotFoundException("Not Found Category: "+id));
		CategoryDto categoryDto=categoryMapper.entityToDto(category);
		
		List<DishDto> dishDtos=category.getDishes().stream()
				.map(DishMapper::entityToDto).collect(Collectors.toList());
		categoryDto.setDishDtos(dishDtos);
		return categoryDto;
	}

	

	
}
