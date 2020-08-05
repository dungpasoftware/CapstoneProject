package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.constant.MessageErrorConsant;
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
				.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_CATEGORY));
		CategoryDto categoryDto = categoryMapper.entityToDto(category);
		return categoryDto;
	}


	@Override
	@Transactional
	public CategoryDto create(CategoryRequest categoryRequest) {

		//create new category
		Category category = new Category();
		//set basic information category
		category.setCategoryName(categoryRequest.getCategoryName());
		category.setDescription(categoryRequest.getDescription());
		category.setImageUrl(categoryRequest.getImageUrl());
		category.setPriority(categoryRequest.getPriority());
		
		Status status=statusRepo.findById(StatusConstant.STATUS_CATEGORY_AVAILABLE)
				.orElseThrow(()-> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_STATUS));
		category.setStatus(status);
		//save category to database
		Category newCategory=categoryRepo.save(category);
		if(newCategory==null) {
			throw new AddException(MessageErrorConsant.ERROR_CREATE_CATEGORY);
		}
		//map entity to dto	
		return categoryMapper.entityToDto(newCategory);
	}

	@Override
	@Transactional
	public CategoryDto update(CategoryRequest categoryRequest, Long id) {
		
		//save newCategory to database
		Category saveCategory= categoryRepo.findById(id)
				.map(category -> {
					category.setCategoryName(categoryRequest.getCategoryName());
					category.setDescription(categoryRequest.getDescription());
					category.setImageUrl(categoryRequest.getImageUrl());
					category.setPriority(categoryRequest.getPriority());
					return categoryRepo.save(category);
				})
				.orElseThrow(()-> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_CATEGORY));
		//map entity to dto
		if(saveCategory==null) {
			throw new UpdateException(MessageErrorConsant.ERROR_UPDATE_CATEGORY);
		}
		return categoryMapper.entityToDto(saveCategory);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		
		Status status = statusRepo.findById(StatusConstant.STATUS_CATEGORY_EXPIRE)
				.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_STATUS));
		Category saveCategory=categoryRepo.findById(id).map(category ->{
			category.setStatus(status);
			return category;
		})
		.orElseThrow(()-> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_CATEGORY));
		
		saveCategory=categoryRepo.save(saveCategory);
		
		if(saveCategory==null) {
			throw new DeleteException(MessageErrorConsant.ERROR_DELETE_CATEGORY);
		}
		
	}

}
