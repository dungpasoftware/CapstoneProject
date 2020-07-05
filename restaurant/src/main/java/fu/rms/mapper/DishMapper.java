package fu.rms.mapper;

import org.springframework.stereotype.Component;

import fu.rms.dto.DishDto;
import fu.rms.dto.DishDto.StatusDish;
import fu.rms.entity.Dish;
import fu.rms.entity.Status;

@Component
public class DishMapper {

	
	
	public DishDto entityToDto(Dish dishEntity) {
		DishDto dishDto=new DishDto();
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
		StatusDish statusDish=new StatusDish(dishEntity.getStatus().getStatusId(),dishEntity.getStatus().getStatusValue());
		dishDto.setStatus(statusDish);		
		return dishDto;
	}
	
	public Dish dtoToEntity(DishDto dishDto) {
		Dish dish=new Dish();
		dish.setDishId(dishDto.getDishId());
		dish.setDishCode(dishDto.getDishCode());
		dish.setDishUnit(dishDto.getDishUnit());
		dish.setDefaultPrice(dishDto.getDefaultPrice());
		dish.setCost(dishDto.getCost());
		dish.setRemainQuantity(dishDto.getRemainQuantity());
		dish.setDescription(dishDto.getDescription());
		dish.setTimeComplete(dishDto.getTimeComplete());
		dish.setTimeNotification(dishDto.getTimeNotification());
		dish.setImage_url(dishDto.getImageUrl());
		Status status=new Status();
		status.setStatusId(dishDto.getStatus().getStatusId());
		status.setStatusName(dishDto.getStatus().getStatusValue());
		return dish;
		
	}
	
}
