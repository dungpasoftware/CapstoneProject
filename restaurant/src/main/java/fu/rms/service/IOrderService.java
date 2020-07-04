package fu.rms.service;

import java.util.Date;
import java.util.List;

import fu.rms.dto.OrderDto;

public interface IOrderService {

	OrderDto getCurrentOrderByTable(Long tableId);
	
	int insertOrder(OrderDto dto);
	
	OrderDto getLastestOrder();
	
	int updateOrderTable(OrderDto dto, Long tableId);
	
	int updateOrderStatus(Long status, Long orderId);
	
	int updateOrderChef(OrderDto dto, Long status);
	
	int updateOrderCashier(OrderDto dto, Long status);
	
	int updatePayOrder(Date paymentDate, Long status, Float timeToComplete, Long orderId);
	
	int updateOrderQuantity(OrderDto dto);
	
	OrderDto getOrderById(Long orderId);
	
	List<OrderDto> getOrder();
}
