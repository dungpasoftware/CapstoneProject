package fu.rms.service.impl;

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

	@Override
	public OrderDto getCurrentOrderByTable(Long tableId) {
		
		Order entity = orderRepo.getCurrentOrderByTable(tableId);
		
		OrderDto dto = orderMapper.entityToDto(entity);
		
		return dto;
		
	}

	@Override
	public int insertOrder(OrderDto dto) {

		return orderRepo.insertOrder(dto.getOrderTakerStaffId(), dto.getTableId(), dto.getStatusId(), 
				dto.getOrderCode(), dto.getTotalItem(), dto.getTotalAmount(), dto.getOrderDate(), "mduc");
		
	}

	@Override
	public Long getLastestOrderId() {
		return orderRepo.getLastestOrderId();
	}

}
