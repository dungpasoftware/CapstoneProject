package fu.rms.service;

import java.util.List;

import fu.rms.dto.OrderDishDto;

public interface IOrderDishService {

	List<OrderDishDto> getListOrderDishByOrder(Long orderId);
	 
	int insertOrderDish(OrderDishDto dto, Long orderId);
	
	int updateStatusOrderDish(Long status, Long orderDishId);
	
	int updateQuantityOrderDish(OrderDishDto dto);
}
