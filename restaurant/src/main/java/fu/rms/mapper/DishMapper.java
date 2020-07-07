package fu.rms.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import fu.rms.dto.DishDto;
import fu.rms.dto.DishDto.CategoryDish;
import fu.rms.dto.DishDto.OptionDish;
import fu.rms.dto.DishDto.StatusDish;
import fu.rms.entity.Dish;

@Component
public class DishMapper {

	public DishDto entityToDto(Dish dishEntity) {
		DishDto dishDto = new DishDto();
		dishDto.setDishId(dishEntity.getDishId());
		dishDto.setDishCode(dishEntity.getDishCode());
		dishDto.setDishName(dishEntity.getDishName());
		dishDto.setDishUnit(dishEntity.getDishUnit());
		dishDto.setDefaultPrice(dishEntity.getDefaultPrice());
		dishDto.setCost(dishEntity.getCost());
		dishDto.setRemainQuantity(dishEntity.getRemainQuantity());
		dishDto.setDescription(dishEntity.getDescription());
		dishDto.setTimeComplete(dishEntity.getTimeComplete());
		dishDto.setTimeNotification(dishEntity.getTimeNotification());
		dishDto.setImageUrl(dishEntity.getImage_url());
		//set status
		if (dishEntity.getStatus() != null) {
			StatusDish statusDish = new StatusDish(dishEntity.getStatus().getStatusId(),
					dishEntity.getStatus().getStatusValue());
			dishDto.setStatus(statusDish);
		}
		// set category
		if (dishEntity.getCategories() != null) {
			List<CategoryDish> categoryDishs = dishEntity.getCategories().stream()
					.map((category) -> new CategoryDish(category.getCategoryId(), category.getCategoryName(),
							category.getImageUrl()))
					.collect(Collectors.toList());
			dishDto.setCategories(categoryDishs);
		}
		// set option
		if (dishEntity.getOptions() != null) {
			List<OptionDish> optionDishs = dishEntity.getOptions().stream()
					.map((option) -> new OptionDish(option.getOptionId(), option.getOptionName()))
					.collect(Collectors.toList());
			dishDto.setOptions(optionDishs);
		}
		return dishDto;
	}

	public Dish dtoToEntity(DishDto dishDto) {
		Dish dish = new Dish();
		dish.setDishId(dishDto.getDishId());
		dish.setDishCode(dishDto.getDishCode());
		dish.setDishName(dishDto.getDishName());
		dish.setDishUnit(dishDto.getDishUnit());
		dish.setDefaultPrice(dishDto.getDefaultPrice());
		dish.setCost(dishDto.getCost());
		dish.setRemainQuantity(dishDto.getRemainQuantity());
		dish.setDescription(dishDto.getDescription());
		dish.setTimeComplete(dishDto.getTimeComplete());
		dish.setTimeNotification(dishDto.getTimeNotification());
		dish.setImage_url(dishDto.getImageUrl());
		return dish;

	}

}
