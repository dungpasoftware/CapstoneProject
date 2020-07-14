package fu.rms.service;

import java.util.List;

import fu.rms.dto.OrderDishDto;
import fu.rms.newDto.SumQuantityAndPrice;

public interface IOrderDishService {

	List<OrderDishDto> getListOrderDishByOrder(Long orderId);
	 
	int insertOrderDish(OrderDishDto dto, Long orderId);
	
	int updateStatusOrderDish(OrderDishDto dto, Long statusId);
	
	int updateQuantityOrderDish(OrderDishDto dto);
	
	SumQuantityAndPrice getSumQtyAndPriceByOrder(Long orderId);
	
	int updateToppingOrderDish(OrderDishDto dto);
	
	OrderDishDto getOrderDishById(Long orderDishId);
	
	int updateCancelOrderDish(OrderDishDto dto);
	
	int getCountCompleteOrder(Long orderId);
}
