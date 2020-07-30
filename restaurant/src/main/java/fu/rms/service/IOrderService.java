package fu.rms.service;

import java.util.List;

import fu.rms.dto.OrderDto;
import fu.rms.newDto.OrderChef;
import fu.rms.newDto.OrderDetail;
import fu.rms.request.OrderChefRequest;

public interface IOrderService {

	OrderDto getCurrentOrderByTable(Long tableId);
	
	OrderDto insertOrder(OrderDto dto);
	
	OrderDto getOrderByCode(String orderCode);
		
	OrderDetail getOrderById(Long orderId);
	
	List<OrderDto> getListOrder();
	
	List<OrderDto> getListByOrderTaker(Long staffId);
	
	String changeOrderTable(OrderDto dto, Long tableId);
	
	int updateCancelOrder(OrderDto dto);
	
	OrderChef updateOrderChef(OrderChefRequest request, Long statusId);
	
	int updatePayOrder(OrderDto dto, Long statusId);
	
	int updateOrderQuantity(Integer totalItem, Double totalAmount, Long orderId);
	
	OrderDetail updateSaveOrder(OrderDto dto);
	
	int updateComment(OrderDto dto);
	
	int updateStatusOrder(OrderDto dto, Long statusId);
	
	List<OrderChef> getListDisplayChefScreen();
	
	OrderChef getOrderChefById(Long orderId);
}
