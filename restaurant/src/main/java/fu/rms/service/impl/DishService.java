package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<DishDto> getAll() {
		List<Dish> dishes = dishRepo.findAll();
		List<DishDto> dishDtos = dishes.stream().map(dishMapper::entityToDto).collect(Collectors.toList());
		return dishDtos;
	}

	@Override
	public List<DishDto> getByCategoryId(Long categoryId) {

		List<Dish> dishes = dishRepo.findByCategoryId(categoryId);

		List<DishDto> dishDtos = dishes.stream().map(dishMapper::entityToDto).collect(Collectors.toList());
		return dishDtos;

	}

}
