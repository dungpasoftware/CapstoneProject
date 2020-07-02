package fu.rms.service;

import java.util.Date;

import fu.rms.dto.OrderDto;

public interface IOrderService {

	OrderDto getCurrentOrderByTable(Long tableId);
	
	int insertOrder(OrderDto dto);
	
	OrderDto getLastestOrder();
	
	int updateOrderTable(Long tableId, Long status);
	
	int updateOrderStatus(Long status, Long orderId);
	
	int updateOrderChef(Long chefId, Long status,  Long orderId);
	
	int updateOrderCashier(Long staffId, Long status, Long orderId);
	
	int updatePayOrder(Date paymentDate, Long status, float timeToComplete, Long orderId);
	
	int updateOrderQuantity(int totalItem, double totalAmount, Long orderId);
}
