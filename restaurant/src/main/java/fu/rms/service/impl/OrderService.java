package fu.rms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.constant.Utils;
import fu.rms.dto.OrderDishDto;
import fu.rms.dto.OrderDto;
import fu.rms.entity.Order;
import fu.rms.entity.OrderDishOption;
import fu.rms.mapper.OrderMapper;
import fu.rms.newDto.OrderDetail;
import fu.rms.newDto.OrderDishOptionDtoNew;
import fu.rms.repository.OrderRepository;
import fu.rms.repository.StaffRepository;
import fu.rms.service.IOrderService;

@Service
public class OrderService implements IOrderService {
	
	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	StaffRepository staffRepo;
	
	@Autowired
	TableService tableService;
	
	@Autowired
	OrderDishService orderDishService;
	
	@Autowired
	OrderDishOptionService orderDishOptionService;

	@Override
	public OrderDto getCurrentOrderByTable(Long tableId) {
		
		Order entity = orderRepo.getCurrentOrderByTable(tableId);
		
		OrderDto dto = orderMapper.entityToDto(entity);
		
		return dto;
		
	}

	/**
	 * tạo mới order
	 */
	@Override
	public OrderDto insertOrder(OrderDto dto) {
		
		String orderCode = Utils.generateOrderCode();
		Date orderDate = Utils.getCurrentTime();
		OrderDto orderDto = null;
		int result=0;
		if(dto != null) {
			String staffCode = staffRepo.findStaffCodeById(dto.getOrderTakerStaffId());
			result = orderRepo.insertOrder(dto.getOrderTakerStaffId(), dto.getTableId(), StatusConstant.STATUS_ORDER_ORDERING, 
					orderCode, orderDate, staffCode);
			if(result == 1) {
				tableService.updateTableNewOrder();
				orderDto = getOrderByCode(orderCode);
			}
		}
		
		return orderDto;
	}

	@Override
	public OrderDto getOrderByCode(String orderCode) {
		Order entity = orderRepo.getOrderByCode(orderCode);
		OrderDto dto = orderMapper.entityToDto(entity);
		return dto;
	}

	/**
	 * Khi order xong
	 */
	@Override
	public int updateOrderOrdered(OrderDto dto) {
		int result = 0;
		if(dto != null) {
			orderRepo.updateOrderOrdered(StatusConstant.STATUS_ORDER_ORDERED, dto.getTotalItem(), 
					dto.getTotalAmount(), dto.getComment(), dto.getOrderId());

			for (OrderDishDto orderDish : dto.getOrderDish()) {
				orderDishService.insertOrderDish(orderDish, dto.getOrderId());
				for (OrderDishOptionDtoNew orderDishOption : orderDish.getOrderDishOptions()) {
					orderDishOptionService.insertOrderDishOption(orderDishOption, orderDish.getOrderDishId());
				}
			}
//			Order entity = orderMapper.dtoToEntity(dto);
//			orderRepo.save(entity);
		}
		return result;
	}
	
	
	// thay đổi bàn
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

	/**
	 * thanh toán
	 */
	@Override
	public int updatePayOrder(Date paymentDate, Long status, Float timeToComplete, Long orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * update về số lượng
	 */
	@Override
	public int updateOrderQuantity(OrderDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * select by id
	 */
	@Override
	public OrderDetail getOrderById(Long orderId) {
		Order entity = orderRepo.getOrderById(orderId);
		OrderDetail detail = orderMapper.entityToDetail(entity);
		return detail;
	}

	/**
	 * lấy tất cả order
	 */
	@Override
	public List<OrderDto> getListOrder() {
		List<Order> listEntity = orderRepo.getListOrder();
		List<OrderDto> listDto = listEntity.stream().map(orderMapper::entityToDto).collect(Collectors.toList());
		return listDto;
	}

	
	
	

}
