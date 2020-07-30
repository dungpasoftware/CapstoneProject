package fu.rms.service;

import java.util.List;

import fu.rms.dto.OrderDishDto;
import fu.rms.newDto.SumQuantityAndPrice;
import fu.rms.newDto.mapper.OrderDishChef;
import fu.rms.request.OrderDishChefRequest;
import fu.rms.request.OrderDishRequest;

public interface IOrderDishService {

	List<OrderDishDto> getListOrderDishByOrder(Long orderId);
	 
	Long insertOrderDish(OrderDishDto dto, Long orderId);
	
//	int updateStatusOrderDish(OrderDishChefRequest request, Long statusId);
	
	String updateQuantityOrderDish(OrderDishDto dto);
	
	int updateToppingCommentOrderDish(OrderDishDto dto);
	
	String updateCancelOrderDish(OrderDishDto dto);
	
//	int updateCommentOrderDish(OrderDishDto dto);
	
	int getCountStatusOrderDish(Long orderId, Long statusId);
	
	OrderDishDto getOrderDishById(Long orderDishId);
	
	SumQuantityAndPrice getSumQtyAndPriceByOrder(Long orderId);
	
	List<OrderDishDto> getCanReturnByOrderId(Long orderId);
	
	String updateReturnDish(List<OrderDishRequest> listOdr);
	
	int updateStatusByDish(OrderDishChefRequest request);
	
	OrderDishChef updateStatusByDishAndOrder(OrderDishChefRequest request);
}
