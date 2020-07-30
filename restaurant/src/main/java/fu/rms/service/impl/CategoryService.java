package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.CategoryDto;
import fu.rms.entity.Category;
import fu.rms.entity.Status;
import fu.rms.exception.AddException;
import fu.rms.exception.DeleteException;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;
import fu.rms.mapper.CategoryMapper;
import fu.rms.repository.CategoryRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.request.CategoryRequest;
import fu.rms.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private StatusRepository statusRepo;
	
	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public List<CategoryDto> getAll() {
		List<Category> categories = categoryRepo.findByStatusId(StatusConstant.STATUS_CATEGORY_AVAILABLE);
		List<CategoryDto> categoryDtos = categories.stream().map(categoryMapper::entityToDto)
				.collect(Collectors.toList());
		return categoryDtos;

	}

	@Override
	public CategoryDto getById(Long id) {
		Category category = categoryRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Not found category: " + id));
		CategoryDto categoryDto = categoryMapper.entityToDto(category);
		return categoryDto;
	}


	@Override
	@Transactional
	public CategoryDto create(CategoryRequest CategoryRequest) {

		//create new category
		Category category = new Category();
		//set basic information category
		category.setCategoryName(CategoryRequest.getCategoryName());
		category.setDescription(CategoryRequest.getDescription());
		category.setImageUrl(CategoryRequest.getImageUrl());
		category.setPriority(CategoryRequest.getPriority());
		
		Status status=statusRepo.findById(StatusConstant.STATUS_CATEGORY_AVAILABLE)
				.orElseThrow(()-> new NotFoundException("Không tìm thấy trạng thái: "+StatusConstant.STATUS_CATEGORY_AVAILABLE));
		category.setStatus(status);
		//save category to database
		Category newCategory=categoryRepo.save(category);
		if(newCategory==null) {
			throw new AddException("Can't add Category");
		}
		//map entity to dto	
		return categoryMapper.entityToDto(newCategory);
	}

	@Override
	@Transactional
	public CategoryDto update(CategoryRequest CategoryRequest, Long id) {
			
		//save newCategory to database
		Category saveCategory= categoryRepo.findById(id)
				.map(category -> {
					category.setCategoryName(CategoryRequest.getCategoryName());
					category.setDescription(CategoryRequest.getDescription());
					category.setImageUrl(CategoryRequest.getImageUrl());
					category.setPriority(CategoryRequest.getPriority());
					return categoryRepo.save(category);
				})
				.orElseThrow(()-> new NotFoundException("Not found Category: "+id));
		//map entity to dto
		if(saveCategory==null) {
			throw new UpdateException("Can't update Category");
		}
		return categoryMapper.entityToDto(saveCategory);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		
		Status status = statusRepo.findById(StatusConstant.STATUS_CATEGORY_EXPIRE)
				.orElseThrow(() -> new NotFoundException("Not found Status: " + StatusConstant.STATUS_CATEGORY_EXPIRE));
		Category saveCategory=categoryRepo.findById(id).map(category ->{
			category.setStatus(status);
			return category;
		})
		.orElseThrow(()-> new NotFoundException("Not found Category: "+id));
		
		saveCategory=categoryRepo.save(saveCategory);
		
		if(saveCategory==null) {
			throw new DeleteException("Can't delete Category");
		}
		
	}

}
