package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.CategoryDto;
import fu.rms.entity.Category;
import fu.rms.exception.NotFoundException;
import fu.rms.mapper.CategoryMapper;
import fu.rms.repository.CategoryRepository;
import fu.rms.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private CategoryMapper categoryMapper;	

	@Override
	public List<CategoryDto> getAll() {
		List<Category> categories = categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map(categoryMapper::entityToDto)
				.collect(Collectors.toList());
		return categoryDtos;

	}

	@Override
	public CategoryDto getById(Long id) {
		Category category = categoryRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Not Found Category: " + id));
		CategoryDto categoryDto = categoryMapper.entityToDto(category);
		return categoryDto;
	}


	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		//map dto to entity
		Category category = categoryMapper.dtoToEntity(categoryDto);
		//save entity to database
		Category newCategory=categoryRepo.save(category);
		//map entity to dto	
		return categoryMapper.entityToDto(newCategory);
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto, Long id) {
		//map dto to entity
		Category newCategory=categoryMapper.dtoToEntity(categoryDto);
		
		//save newCategory to database
		Category saveCategory= categoryRepo.findById(id)
				.map(category -> {
					category.setCategoryId(newCategory.getCategoryId());
					category.setCategoryName(newCategory.getCategoryName());
					category.setDescription(newCategory.getDescription());
					category.setImageUrl(newCategory.getImageUrl());
					category.setPriority(newCategory.getPriority());
					return categoryRepo.save(category);
				})
				.orElseThrow(()-> new NotFoundException("Not Found Category: "+id));
		//map entity to dto
		return categoryMapper.entityToDto(saveCategory);
	}
	
	@Override
	public void delete(Long id) {
		categoryRepo.deleteById(id);
		
	}

}