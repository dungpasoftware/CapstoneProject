package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.OrderDishDto;
import fu.rms.entity.OrderDish;

@Component
public class OrderDishMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public OrderDishDto entityToDto(OrderDish entity) {
//		
//		OrderDishDto dto = new OrderDishDto();
//		dto.setOrderDishId(entity.getOrderDishId());
//		dto.setQuantity(entity.getQuantity());
//		dto.setSellPrice(entity.getSellPrice());
//		dto.setStatusOrderDishId(entity.getStatus().getStatusId());
//		dto.setStatusOrderDishValue(entity.getStatus().getStatusValue());
//		dto.setOrderId(orderId);
//		
		OrderDishDto dto = modelMapper.map(entity, OrderDishDto.class);
		return dto;
	}
	
	public OrderDish dtoToEntity(OrderDishDto dto) {
		
		OrderDish entity = new OrderDish();
		entity.setOrderDishId(dto.getOrderDishId());
		entity.setQuantity(dto.getQuantity());
		entity.setQuantityCancel(dto.getQuantityCancel());
		entity.setQuantityOk(dto.getQuantityOk());
		entity.setComment(dto.getComment());
		entity.setModifiedBy(dto.getModifiedBy());
		entity.setModifiedDate(dto.getModifiedDate());
		entity.setCreateBy(dto.getCreateBy());
		entity.setCreateDate(dto.getCreateDate());
		entity.setSellPrice(dto.getSellPrice());
		entity.setSumPrice(dto.getSumPrice());
		return entity;
	}
}
