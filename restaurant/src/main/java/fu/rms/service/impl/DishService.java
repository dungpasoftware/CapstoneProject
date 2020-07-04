package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.DishDto;
import fu.rms.entity.Dish;
import fu.rms.exception.NotFoundException;
import fu.rms.mapper.DishMapper;
import fu.rms.repository.DishRepository;
import fu.rms.service.IDishService;

@Service
public class DishService implements IDishService {

	@Autowired
	private DishRepository dishRepo;

	@Autowired
	private DishMapper dishMapper;

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
	public DishDto updateRemainQuantity(Long dishId, int quantity) {
		
		Dish dish=dishRepo.findById(dishId)
				.orElseThrow(() -> new NotFoundException("Not Found Dish: "+dishId));
		DishDto dishDto=null;
		int remainQuantity=dish.getRemainQuantity();
		if(remainQuantity >= quantity) {
			remainQuantity=remainQuantity-quantity;
			dish.setRemainQuantity(remainQuantity);
			Dish newDish=dishRepo.save(dish);
			dishDto=dishMapper.entityToDto(newDish);
			return dishDto;
		}else {
			return null;
		}	
		
	}

	

}
