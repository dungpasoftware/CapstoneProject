package fu.rms.service;

import fu.rms.newDto.OrderDishOptionDtoNew;

public interface IOrderDishOptionService {

	int insertOrderDishOption(OrderDishOptionDtoNew dto, Long orderDishId);
	
	int updateQuantityOrderDishOption(OrderDishOptionDtoNew dto);
	
	int updateCancelOrderDishOption(Long orderDishId, Long statusId);
	
	int deleteOrderDishOption(Long orderDishId);
}
