package fu.rms.service;

import java.util.Date;
import java.util.List;

import fu.rms.dto.OrderDto;
import fu.rms.newDto.OrderDetail;

public interface IOrderService {

	OrderDto getCurrentOrderByTable(Long tableId);
	
	OrderDto insertOrder(OrderDto dto);
	
	OrderDto getOrderByCode(String orderCode);
		
	OrderDetail getOrderById(Long orderId);
	
	List<OrderDto> getListOrder();
	
	int updateOrderTable(OrderDto dto, Long tableId);
	
	int updateOrderStatus(Long status, Long orderId);
	
	int updateOrderChef(OrderDto dto, Long status);
	
	int updateOrderCashier(OrderDto dto, Long status);
	
	int updatePayOrder(Date paymentDate, Long status, Float timeToComplete, Long orderId);
	
	int updateOrderQuantity(OrderDto dto);
	
	int updateOrderOrdered(OrderDto dto);

}
