package fu.rms.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.CategoryDto;
import fu.rms.dto.DishDto;
import fu.rms.dto.OptionDto;
import fu.rms.dto.QuantifierDto;
import fu.rms.entity.Dish;

@Component
public class DishMapper {
	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private OptionMapper optionMapper;
	
	@Autowired
	private QuantifierMapper quantifierMapper;
	
	public DishDto entityToDto(Dish dishEntity) {
		DishDto dishDto = new DishDto();
		dishDto.setDishId(dishEntity.getDishId());
		dishDto.setDishCode(dishEntity.getDishCode());
		dishDto.setDishName(dishEntity.getDishName());
		dishDto.setDishUnit(dishEntity.getDishUnit());
		dishDto.setDefaultPrice(dishEntity.getDefaultPrice());
		dishDto.setCost(dishEntity.getCost());
		dishDto.setDishCost(dishEntity.getDishCost());
		dishDto.setRemainQuantity(dishEntity.getRemainQuantity());
		dishDto.setDescription(dishEntity.getDescription());
		dishDto.setTimeComplete(dishEntity.getTimeComplete());
		dishDto.setImageUrl(dishEntity.getImageUrl());
		dishDto.setTypeReturn(dishEntity.getTypeReturn());
		// set category
		if (dishEntity.getCategories() != null) {
			List<CategoryDto> categoryDishs = dishEntity.getCategories().stream()
					.map(categoryMapper::entityToDto)
					.collect(Collectors.toList());
			dishDto.setCategories(categoryDishs);
		}
		// set option
		if (dishEntity.getOptions() != null) {
			List<OptionDto> optionDtos=dishEntity.getOptions().stream()
					.map(optionMapper::entityToDTo)
					.collect(Collectors.toList());
			dishDto.setOptions(optionDtos);
		}
		//set quantifier
		if(dishEntity.getQuantifiers()!=null) {
			List<QuantifierDto> quantifierDtos=dishEntity.getQuantifiers().stream()
					.map(quantifierMapper::entityToDto)
					.collect(Collectors.toList());
			dishDto.setQuantifiers(quantifierDtos);
		}
		return dishDto;
	}


}
