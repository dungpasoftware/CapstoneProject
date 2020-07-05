package fu.rms.newDto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.entity.OrderDish;
import fu.rms.entity.OrderDishOption;
import fu.rms.newDto.OrderDishOptionDtoNew;

@Component
public class OrderDishOptionMapper {
	@Autowired
	ModelMapper modelMapper;
	
	public OrderDishOptionDtoNew entityToDto(OrderDishOption entity) {
		
		OrderDishOptionDtoNew dto = new OrderDishOptionDtoNew();
		dto.setQuantity(entity.getQuantity());
		dto.setSumPrice(entity.getSumPrice());
//		dto.setStatusStatusDisId(entity.getStatus().getStatusId());
//		dto.setStatusOrderDishValue(entity.getStatus().getStatusValue());
//		dto.setOrderId(orderId);
//		
		return dto;
	}
//	
//	public OrderDish dtoToEntity(OrderDishOptionDtoNew dto) {
//		
//		OrderDish entity = modelMapper.map(dto, OrderDish.class);;
//		
//		return entity;
//	}
}
