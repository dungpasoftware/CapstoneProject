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
import fu.rms.mapper.OrderMapper;
import fu.rms.newDto.GetByDish;
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
		OrderDto orderDto = null;
		int result=0;
		if(dto != null) {
			String staffCode = staffRepo.findStaffCodeById(dto.getOrderTakerStaffId());
			result = orderRepo.insertOrder(dto.getOrderTakerStaffId(), dto.getTableId(), StatusConstant.STATUS_ORDER_ORDERING, 
					orderCode, staffCode);
			if(result == 1) {
				orderDto = getOrderByCode(orderCode);
				tableService.updateTableNewOrder(orderDto);
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
	 * Khi order xong: save order
	 */
	@Override
	public int updateSaveOrder(OrderDto dto) {
		int result = 0;
		if(dto != null) {
			if(dto.getOrderDish() == null || dto.getOrderDish().size() == 0 ) {
			}else {
				for (OrderDishDto orderDish : dto.getOrderDish()) {
					Long orderDishId = orderDishService.insertOrderDish(orderDish, dto.getOrderId());
					if(orderDish.getOrderDishOptions() == null || orderDish.getOrderDishOptions().size() == 0) {
					}else{
						for (OrderDishOptionDtoNew orderDishOption : orderDish.getOrderDishOptions()) {
							orderDishOptionService.insertOrderDishOption(orderDishOption, orderDishId);
						}
					}
				}
			}
			try {
				// chưa order thì update trạng thái, ngày order
				if(dto.getStatusId() == StatusConstant.STATUS_ORDER_ORDERING) {
					Date orderDate = Utils.getCurrentTime();
					orderRepo.updateSaveOrder(StatusConstant.STATUS_ORDER_ORDERED, orderDate, dto.getTotalItem(), 
							dto.getTotalAmount(), dto.getComment(), dto.getOrderId());
					result = tableService.updateStatusOrdered(dto.getTableId(), StatusConstant.STATUS_TABLE_ORDERED);
				} else { // nếu đã order rồi thì chỉ update số lượng và giá
					updateOrderQuantity(dto.getTotalItem(), dto.getTotalAmount(), dto.getOrderId());
				}	
			} catch (NullPointerException e) {
				return 0;
			}
			
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

	/**
	 * hủy order
	 */
	@Override
	public int updateCancelOrder(OrderDto dto, Long statusId) {
		int result = 0;
		if(dto != null) {
			for (OrderDishDto orderDish : dto.getOrderDish()) {
				orderDishService.updateStatusOrderDish(orderDish, StatusConstant.STATUS_ORDER_DISH_CANCELED);
			}
			result = orderRepo.updateCancelOrder(statusId, Utils.getCurrentTime(), dto.getModifiedBy(), dto.getComment(), dto.getOrderId());
		}
		return result;
	}

	/**
	 * bếp nhấn xác nhận đã nhân order: PREPARATION, bắt dầu nấu. Nếu status là JUST_COOKED thì là đã nấu xong
	 */
	@Override
	public int updateOrderChef(OrderDto dto, Long statusId) {

		int result = 0;
		if(dto != null) {
			if(statusId == StatusConstant.STATUS_ORDER_PREPARATION) {
				for (OrderDishDto orderDish : dto.getOrderDish()) {
					orderDishService.updateStatusOrderDish(orderDish, StatusConstant.STATUS_ORDER_DISH_PREPARATION);
				}
			}
			result = orderRepo.updateOrderChef(dto.getChefStaffId(), statusId, dto.getOrderId());
		}
		return result;

	}

	/**
	 * thanh toán
	 */
	@Override
	public int updatePayOrder(OrderDto dto, Long statusId) {
		int result = 0;
		String timeToComplete = Utils.getOrderTime(Utils.getCurrentTime(), dto.getOrderDate());
		if(dto != null) {
			result = orderRepo.updatePayOrder(Utils.getCurrentTime(), dto.getCashierStaffId(), statusId, timeToComplete, dto.getOrderId());
		}
		return result;
	}

	/**
	 * update về số lượng
	 */
	@Override
	public int updateOrderQuantity(int totalItem, double totalAmount, Long orderId) {
		int result = 0;
		result = orderRepo.updateOrderQuantity(totalItem, totalAmount, orderId);
		return result;
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

	@Override
	public List<OrderDto> getListByOrderTaker(Long staffId) {
//		List<Order> listEntity = orderRepo.findByOrderTakerStaffId(staffId);
//		List<OrderDto> listDto =  listEntity.stream().map(orderMapper::entityToDto).collect(Collectors.toList());
		return null;
	}

	/**
	 * xác nhận bếp đã thực hiện xong món hoặc ordertaker trả món xong
	 */
	@Override
	public int updateStatusOrder(OrderDto dto, Long statusId) {
		
		int result = 0;
		if(dto != null) {
			if(statusId == StatusConstant.STATUS_ORDER_JUST_COOKED && dto.getOrderDish().size() != 0){
				for (OrderDishDto orderDish : dto.getOrderDish()) {
					orderDishService.updateStatusOrderDish(orderDish, StatusConstant.STATUS_ORDER_DISH_JUST_COOKED);
				}
			}else if(statusId == StatusConstant.STATUS_ORDER_COMPLETED && dto.getOrderDish().size() != 0){
				for (OrderDishDto orderDish : dto.getOrderDish()){
					orderDishService.updateStatusOrderDish(orderDish, StatusConstant.STATUS_ORDER_DISH_COMPLETED);
				}
			} 
			result = orderRepo.updateStatusOrder(statusId, dto.getOrderId());
		}
		
		return result;
	}

	@Override
	public List<GetByDish> getByDish() {
		List<GetByDish> list = orderRepo.getByDish();
		return list;
	}



}
