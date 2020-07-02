package fu.rms.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.OrderDto;
import fu.rms.entity.Order;
import fu.rms.mapper.OrderMapper;
import fu.rms.repository.OrderRepository;
import fu.rms.service.IOrderService;

@Service
public class OrderService implements IOrderService {
	
	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	TableService tableService;

	@Override
	public OrderDto getCurrentOrderByTable(Long tableId) {
		
		Order entity = orderRepo.getCurrentOrderByTable(tableId);
		
		OrderDto dto = orderMapper.entityToDto(entity);
		
		return dto;
		
	}

	@Override
	public int insertOrder(OrderDto dto) {
		int result = orderRepo.insertOrder(dto.getOrderTakerStaffId(), dto.getTableId(), dto.getStatusId(), 
				dto.getOrderCode(), dto.getTotalItem(), dto.getTotalAmount(), dto.getOrderDate(), "mduc");
		if(result == 1) {
			tableService.updateTableNewOrder();
		}
		return result;
	}

	@Override
	public OrderDto getLastestOrder() {
		Order entity = orderRepo.getLastestOrder();
		OrderDto dto = orderMapper.entityToDto(entity);
		return dto;
	}

	@Override
	public int updateOrderTable(Long tableId, Long status) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateOrderStatus(Long status, Long orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	
	public int updateOrderChef(Long chefId, Long status, Long orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateOrderCashier(Long staffId, Long status, Long orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePayOrder(Date paymentDate, Long status, float timeToComplete, Long orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateOrderQuantity(int totalItem, double totalAmount, Long orderId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
