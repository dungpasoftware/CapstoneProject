package fu.rms.service;

import fu.rms.newDto.OrderDishOptionDto;

public interface IOrderDishOptionService {

	int insertOrderDishOption(OrderDishOptionDto dto, Long orderDishId);
	
	int updateQuantityOrderDishOption(OrderDishOptionDto dto);
	
	int updateCancelOrderDishOption(Long orderDishId, Long statusId);
	
	int deleteOrderDishOption(Long orderDishId);
}
