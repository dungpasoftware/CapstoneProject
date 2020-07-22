package fu.rms.mapper;

import org.springframework.stereotype.Component;

import fu.rms.dto.OrderDishCancelDto;
import fu.rms.entity.OrderDishCancel;

@Component
public class OrderDishCancelMapper {

	public OrderDishCancelDto entityToDto(OrderDishCancel orderDishCancel) {
		OrderDishCancelDto orderDishCancelDto=new OrderDishCancelDto();
		orderDishCancelDto.setOrderDishCancelId(orderDishCancel.getOrderDishCancelId());
		orderDishCancelDto.setQuantityCancel(orderDishCancel.getQuantityCancel());
		orderDishCancelDto.setCancelBy(orderDishCancel.getCancelBy());
		orderDishCancelDto.setCancelDate(orderDishCancel.getCancelDate());
		orderDishCancelDto.setOrderDishId(orderDishCancel.getOrderDish().getOrderDishId());
		return orderDishCancelDto;
	}
	
	public OrderDishCancel dtoToEntity(OrderDishCancelDto orderDishCancelDto) {
		OrderDishCancel orderDishCancel=new OrderDishCancel();
		orderDishCancel.setOrderDishCancelId(orderDishCancelDto.getOrderDishCancelId());
		orderDishCancel.setQuantityCancel(orderDishCancelDto.getQuantityCancel());
		orderDishCancel.setCancelBy(orderDishCancelDto.getCancelBy());
		orderDishCancel.setCancelDate(orderDishCancelDto.getCancelDate());
		return orderDishCancel;
	}
}
