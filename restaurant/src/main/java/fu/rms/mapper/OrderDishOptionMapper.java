package fu.rms.mapper;

import org.springframework.stereotype.Component;

import fu.rms.entity.OrderDishOption;
import fu.rms.newDto.OrderDishOptionChef;
import fu.rms.newDto.OrderDishOptionDto;

@Component
public class OrderDishOptionMapper {
	
	public OrderDishOptionDto entityToDto(OrderDishOption entity) {
		
		OrderDishOptionDto dto = new OrderDishOptionDto();
		dto.setOrderDishOptionId(entity.getOrderDishOptionId());
		dto.setOptionId(entity.getOption().getOptionId());
		dto.setOptionName(entity.getOption().getOptionName());
		dto.setOptionType(entity.getOption().getOptionType());
		dto.setOptionUnit(entity.getOption().getUnit());
		dto.setQuantity(entity.getQuantity());
		dto.setOptionPrice(entity.getOption().getPrice());
		dto.setSumPrice(entity.getSumPrice());	
		return dto;
	}
	
	public OrderDishOptionChef entityToChef(OrderDishOption entity) {
		OrderDishOptionChef odoChef = new OrderDishOptionChef();
		odoChef.setOptionName(entity.getOption().getOptionName());
		odoChef.setOptionType(entity.getOption().getOptionType());
		odoChef.setOptionUnit(entity.getOption().getUnit());
		odoChef.setQuantity(entity.getQuantity());
		return odoChef;
	}
}
