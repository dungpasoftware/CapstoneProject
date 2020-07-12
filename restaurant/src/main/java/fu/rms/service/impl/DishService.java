package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.DishDto;
import fu.rms.dto.DishDto.CategoryDish;
import fu.rms.dto.DishDto.OptionDish;
import fu.rms.entity.Category;
import fu.rms.entity.Dish;
import fu.rms.entity.Option;
import fu.rms.entity.Status;
import fu.rms.exception.NotFoundException;
import fu.rms.mapper.DishMapper;
import fu.rms.repository.CategoryRepository;
import fu.rms.repository.DishRepository;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.service.IDishService;

@Service
public class DishService implements IDishService {

	@Autowired
	private DishRepository dishRepo;

	@Autowired
	private StatusRepository statusRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private OptionRepository optionRepo;

	@Autowired
	private DishMapper dishMapper;

	@Override
	public List<DishDto> getAll() {
		List<Dish> dishes = dishRepo.findByStatusId(StatusConstant.STATUS_DISH_AVAILABLE);
		List<DishDto> dishDtos = dishes.stream().map(dishMapper::entityToDto).collect(Collectors.toList());
		return dishDtos;
	}

	@Override
	public DishDto getById(Long id) {
		Dish dish = dishRepo.findById(id).orElseThrow(() -> new NotFoundException("Not Found Dish: " + id));
		DishDto dishDto = dishMapper.entityToDto(dish);
		return dishDto;
	}

	@Override
	public List<DishDto> getByCategoryId(Long categoryId) {
		List<Dish> dishes = dishRepo.findByCategoryIdAndStatusId(categoryId, StatusConstant.STATUS_DISH_AVAILABLE);
		List<DishDto> dishDtos = dishes.stream().map(dishMapper::entityToDto).collect(Collectors.toList());
		return dishDtos;
	}

	@Override
	public DishDto create(DishDto dishDto) {
		// mapper entity
		
		Dish dish = dishMapper.dtoToEntity(dishDto);
		// set status
		Status status=null;
		if(dish.getRemainQuantity()>0) {
			status=statusRepo.findById(StatusConstant.STATUS_DISH_AVAILABLE)
					.orElseThrow(()-> new NotFoundException("Not found Status: "+StatusConstant.STATUS_DISH_AVAILABLE));
		}else {
			status=statusRepo.findById(StatusConstant.STATUS_DISH_OVER)
					.orElseThrow(()-> new NotFoundException("Not found Status: "+StatusConstant.STATUS_DISH_OVER));
		}
		dish.setStatus(status);
		// set category
		List<Category> categories = null;
		if (dishDto.getCategories() != null) {
			categories = new ArrayList<>();	
			for (CategoryDish categoryDish : dishDto.getCategories()) {
				Category category = categoryRepo.findById(categoryDish.getCategoryId()).orElseThrow(
						() -> new NotFoundException("Not found Category: " + categoryDish.getCategoryId()));
				categories.add(category);
			}
			dish.setCategories(categories);
		}

		// set option
		List<Option> options = null;
		if (dishDto.getOptions() != null) {
			options = new ArrayList<>();
			for (OptionDish optionDish : dishDto.getOptions()) {
				Option option = optionRepo.findById(optionDish.getOptionId())
						.orElseThrow(() -> new NotFoundException("Not found Option: " + optionDish.getOptionId()));
				options.add(option);
			}
			dish.setOptions(options);
		}

		Dish newDish = dishRepo.save(dish);

		// mapper dto
		dishDto = dishMapper.entityToDto(newDish);
		return dishDto;
	}

	@Override
	public DishDto update(DishDto dishDto, Long id) {
		// mapper entity
		Dish dish = dishMapper.dtoToEntity(dishDto);
		// set status
		if (dishDto.getStatus() != null) {
			Status status = statusRepo.findById(dishDto.getStatus().getStatusId()).get();
			dish.setStatus(status);
		}
		// set category
		List<Category> categories = null;
		if (dishDto.getCategories() != null) {
			categories = new ArrayList<>();
			for (CategoryDish categoryDish : dishDto.getCategories()) {
				Category category = categoryRepo.findById(categoryDish.getCategoryId()).orElseThrow(
						() -> new NotFoundException("Not found Category: " + categoryDish.getCategoryId()));
				categories.add(category);
			}
			dish.setCategories(categories);
		}

		// set option
		List<Option> options = null;
		if (dishDto.getOptions() != null) {
			options = new ArrayList<>();
			for (OptionDish optionDish : dishDto.getOptions()) {
				Option option = optionRepo.findById(optionDish.getOptionId())
						.orElseThrow(() -> new NotFoundException("Not found Option: " + optionDish.getOptionId()));
				options.add(option);
			}
			dish.setOptions(options);
		}

		Dish newDish = dishRepo.save(dish);

		// mapper dto
		dishDto = dishMapper.entityToDto(newDish);
		return dishDto;

	}

	@Override
	public void delete(Long[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			for (Long id : ids) {
				dishRepo.updateStatus(id, StatusConstant.STATUS_DISH_EXPIRE);
			}
		}

	}

	@Override
	public List<DishDto> search(String dishName) {
		Page<Dish> page=dishRepo.search(dishName,PageRequest.of(0, 5));
		List<Dish> dishes= page.getContent();
		System.out.println(page.getTotalPages());
		return dishes.stream().map(dishMapper::entityToDto).collect(Collectors.toList());
		
	}

}
