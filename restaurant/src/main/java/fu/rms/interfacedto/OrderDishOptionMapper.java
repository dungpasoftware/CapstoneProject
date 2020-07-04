package fu.rms.interfacedto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.entity.OrderDish;
import fu.rms.entity.OrderDishOption;

@Component
public class OrderDishOptionMapper {
	@Autowired
	ModelMapper modelMapper;
	
	public OrderDishOptionDto entityToDto(OrderDishOption entity) {
		
		OrderDishOptionDto dto = new OrderDishOptionDto();
		dto.setQuantity(entity.getQuantity());
		dto.setSumPrice(entity.getSumPrice());
//		dto.setStatusStatusDisId(entity.getStatus().getStatusId());
//		dto.setStatusOrderDishValue(entity.getStatus().getStatusValue());
//		dto.setOrderId(orderId);
//		
		return dto;
	}
	
	public OrderDish dtoToEntity(OrderDishOptionDto dto) {
		
		OrderDish entity = modelMapper.map(dto, OrderDish.class);;
		
		return entity;
	}
}
