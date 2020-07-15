package fu.rms.service;

import java.util.List;

import fu.rms.dto.OrderDto;
import fu.rms.newDto.GetByDish;
import fu.rms.newDto.OrderDetail;

public interface IOrderService {

	OrderDto getCurrentOrderByTable(Long tableId);
	
	OrderDto insertOrder(OrderDto dto);
	
	OrderDto getOrderByCode(String orderCode);
		
	OrderDetail getOrderById(Long orderId);
	
	List<OrderDto> getListOrder();
	
	List<OrderDto> getListByOrderTaker(Long staffId);
	
	int updateOrderTable(OrderDto dto, Long tableId);
	
	int updateCancelOrder(OrderDto dto, Long statusId);
	
	int updateOrderChef(OrderDto dto, Long statusId);
	
//	int updateOrderCashier(OrderDto dto, Long statusId);
	
	int updatePayOrder(OrderDto dto, Long statusId);
	
	int updateOrderQuantity(int totalItem, double totalAmount, Long orderId);
	
	int updateSaveOrder(OrderDto dto);
	
	int updateStatusOrder(OrderDto dto, Long statusId);
	
	List<GetByDish> getByDish();
}
