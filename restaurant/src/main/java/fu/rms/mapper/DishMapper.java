package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.DishDto;
import fu.rms.entity.Dish;

@Component
public class DishMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	public DishDto entityToDto(Dish dish) {
		DishDto dishDto=modelMapper.map(dish,DishDto.class);
		return dishDto;
	}
	
	public Dish dtoToEntity(DishDto dishDto) {
		Dish dish=modelMapper.map(dishDto,Dish.class);
		return dish;
	}
	
}
