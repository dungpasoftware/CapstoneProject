package fu.rms.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.constant.Utils;
import fu.rms.dto.OrderDto;
import fu.rms.entity.Order;
import fu.rms.newDto.OrderDetail;

@Component
public class OrderMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public OrderDto entityToDto(Order entity) {
		
		OrderDto dto = modelMapper.map(entity, OrderDto.class);
		return dto;
	}
	
	public Order dtoToEntity(OrderDto dto) {
		Order entity = modelMapper.map(dto, Order.class);
//		entity.setComment(dto.getComment());
//		entity.setCreateBy(dto.getCreateBy());
//		entity.setOrderCode(dto.getOrderCode());
//		entity.setOrderDate(dto.getOrderDate());
//		entity.setO
		return entity;
	}
	
	public OrderDetail entityToDetail(Order entity) {
		OrderDetail detail = new OrderDetail();
		if(entity != null) {
			detail = modelMapper.map(entity, OrderDetail.class);
		}
		return detail;
	}
	
}

