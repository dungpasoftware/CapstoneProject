package fu.rms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.constant.Utils;
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
		
		String orderCode = Utils.generateOrderCode();
		Date orderDate = Utils.getCurrentTime();
		int result=0;
		if(dto != null) {
			result = orderRepo.insertOrder(dto.getOrderTakerStaffId(), dto.getTableId(), StatusConstant.STATUS_ORDER_ORDERED, 
					orderCode, dto.getTotalItem(), dto.getTotalAmount(), orderDate, "mduc");
			if(result == 1) {
				tableService.updateTableNewOrder();
			}
		}
		
		return result;
	}

	@Override
	public OrderDto getLastestOrder() {
		Order entity = orderRepo.getLastestOrder();
		OrderDto dto = orderMapper.entityToDto(entity);
		return dto;
	}

	// change table
	@Override
	public int updateOrderTable(OrderDto dto, Long tableId) {
		
		int result = 0;
		if(dto != null) {
			result = orderRepo.updateOrderTable(tableId, dto.getModifiedBy(), Utils.getCurrentTime(), dto.getOrderId());
		}
		return result;
	}

	@Override
	public int updateOrderStatus(Long status, Long orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * bếp nhấn xác nhận đã nhân order: COMFIRMED, bắt dầu nấu. Nếu status là JUST_COOKED thì là đã nấu xong
	 */
	@Override
	public int updateOrderChef(OrderDto dto, Long status) {

		int result = 0;
		if(dto != null) {
			result = orderRepo.updateOrderChef(dto.getChefStaffId(), status, dto.getOrderId());
		}
		return result;

	}

	/*
	 * thu ngân liên hệ với order taker xuống lấy phiếu order
	 */
	@Override
	public int updateOrderCashier(OrderDto dto, Long status) {

		int result = 0;
		if(dto != null) {
			result = orderRepo.updateOrderCashier(dto.getCashierStaffId(), StatusConstant.STATUS_ORDER_WAITTING_FOR_PAY, dto.getOrderId());
		}
		return result;
		
	}

	@Override
	public int updatePayOrder(Date paymentDate, Long status, Float timeToComplete, Long orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateOrderQuantity(OrderDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OrderDto getOrderById(Long orderId) {
		Order entity = orderRepo.getOrderById(orderId);
		OrderDto dto = orderMapper.entityToDto(entity);
		return dto;
	}

	@Override
	public List<OrderDto> getOrder() {
		List<Order> listEntity = orderRepo.getOrder();
		List<OrderDto> listDto = listEntity.stream().map(orderMapper::entityToDto).collect(Collectors.toList());
		return listDto;
	}
	
	

}
