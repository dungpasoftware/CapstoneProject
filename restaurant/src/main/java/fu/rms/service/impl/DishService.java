package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.DishDto;
import fu.rms.dto.DishDto.CategoryDish;
import fu.rms.dto.DishDto.OptionDish;
import fu.rms.dto.DishDto.StatusDish;
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
	private StatusRepository statusRepository;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private OptionRepository optionRepo;

	@Autowired
	private DishMapper dishMapper;
	
	
	@Override
	public List<DishDto> getAll() {
		List<Dish> dishes=dishRepo.findAll();
		List<DishDto> dishDtos=dishes.stream().map(dishMapper::entityToDto).collect(Collectors.toList());
		return dishDtos;
	}

	@Override
	public DishDto getById(Long id) {
		Dish dish = dishRepo.findById(id).orElseThrow(() -> new NotFoundException("Not Found Dish: "+id));
		DishDto dishDto = dishMapper.entityToDto(dish);
		return dishDto;
	}


	@Override
	public List<DishDto> getByCategoryId(Long categoryId) {
		List<Long> statuses=new ArrayList<>();
		statuses.add(StatusConstant.STATUS_DISH_AVAILABLE);
		List<Dish> dishes = dishRepo.findByCategoryIdAndStatusIn(categoryId, statuses);
		List<DishDto> dishDtos = dishes.stream().map(dishMapper::entityToDto).collect(Collectors.toList());
		return dishDtos;

	}


	@Override
	public DishDto save(DishDto dishDto) {
		//mapper entity
		Dish dish=dishMapper.dtoToEntity(dishDto);
		//set status
		if(dishDto.getStatus()!=null) {
			Status status=statusRepository.findById(dishDto.getStatus().getStatusId()).get();
			dish.setStatus(status);
		}
		//set category
		List<Category> categories=null;
		if(dishDto.getCategories()!=null) {
			categories=new ArrayList<>();
			for (CategoryDish categoryDish : dishDto.getCategories()) {
				Category category=categoryRepo.findById(categoryDish.getCategoryId())
						.orElseThrow(()-> new NotFoundException("Not found Category: "+categoryDish.getCategoryId()));
				categories.add(category);
			}
			dish.setCategories(categories);
		}
		
		//set option
		List<Option> options=null;
		if(dishDto.getOptions()!=null) {
			options=new ArrayList<>();
			for (OptionDish optionDish : dishDto.getOptions()) {
				Option option=optionRepo.findById(optionDish.getOptionId())
						.orElseThrow(()-> new NotFoundException("Not found Option: "+optionDish.getOptionId()));
				options.add(option);
			}
			dish.setOptions(options);
		}
		
		Dish newDish=dishRepo.save(dish);
		
		//mapper dto
		dishDto=dishMapper.entityToDto(newDish);
		//set status
		if(newDish.getStatus()!=null) {
			StatusDish statusDish=new StatusDish(newDish.getStatus().getStatusId(),newDish.getStatus().getStatusValue());	
			dishDto.setStatus(statusDish);	
		}
		//set category
		if(newDish.getCategories()!=null) {
			List<CategoryDish> categoryDishs=newDish.getCategories().stream()
					.map((category) -> new CategoryDish(category.getCategoryId(),category.getCategoryName(),category.getImageUrl())).collect(Collectors.toList());
			dishDto.setCategories(categoryDishs);
		}
		//set option
		if(newDish.getOptions()!=null) {
			List<OptionDish> optionDishs=newDish.getOptions().stream()
					.map((option) -> new OptionDish(option.getOptionId(),option.getOptionName())).collect(Collectors.toList());
			dishDto.setOptions(optionDishs);	
		}
		return dishDto;	
	}



	

}
