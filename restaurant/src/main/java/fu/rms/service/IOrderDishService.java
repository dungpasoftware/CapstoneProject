package fu.rms.service;

import java.util.List;

import fu.rms.dto.OrderDishDto;
import fu.rms.newDto.SumQuantityAndPrice;

public interface IOrderDishService {

	List<OrderDishDto> getListOrderDishByOrder(Long orderId);
	 
	Long insertOrderDish(OrderDishDto dto, Long orderId);
	
	int updateStatusOrderDish(OrderDishDto dto, Long statusId);
	
	int updateQuantityOrderDish(OrderDishDto dto);
	
	int updateToppingOrderDish(OrderDishDto dto);
	
	int updateCancelOrderDish(OrderDishDto dto);
	
	int getCountCompleteOrder(Long orderId);
	
	OrderDishDto getOrderDishById(Long orderDishId);
	
	SumQuantityAndPrice getSumQtyAndPriceByOrder(Long orderId);
}
