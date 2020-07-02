package fu.rms.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.constant.Utils;
import fu.rms.dto.OrderDto;
import fu.rms.entity.Order;

@Component
public class OrderMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public OrderDto entityToDto(Order entity) {
		
		OrderDto dto = modelMapper.map(entity, OrderDto.class);
		String orderTime = Utils.getOrderTime(Utils.getCurrentTime(), dto.getOrderDate());
		dto.setOrderTime(orderTime);
		return dto;
	}
	
	public Order dtoToEntity(OrderDto dto) {
		
		Order entity = modelMapper.map(dto, Order.class);
		return entity;
	}
	
}

