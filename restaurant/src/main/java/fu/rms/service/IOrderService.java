package fu.rms.service;

import java.util.List;

import fu.rms.dto.OrderChef;
import fu.rms.dto.OrderDetail;
import fu.rms.dto.OrderDto;
import fu.rms.request.OrderRequest;

public interface IOrderService {
	
	OrderDto insertOrder(OrderDto dto);
	
	OrderDto getOrderByCode(String orderCode);
		
	OrderDetail getOrderDetailById(Long orderId);
	
	String changeOrderTable(OrderDto dto, Long tableId);
	
	int updateCancelOrder(OrderDto dto);
	
	OrderChef updateOrderChef(OrderRequest request);
	
	int updatePaymentOrder(OrderRequest request);
	
	int updateOrderQuantity(Integer totalItem, Double totalAmount, Long orderId);
	
	OrderDetail updateSaveOrder(OrderDto dto);
	
	int updateComment(OrderRequest request);
	
	String updateWaitingPayOrder(OrderRequest request);
	
	List<OrderChef> getListDisplayChefScreen();
	
	OrderChef getOrderChefById(Long orderId);
	
	String updateAcceptPaymentOrder(OrderRequest request, Integer accept);

}
