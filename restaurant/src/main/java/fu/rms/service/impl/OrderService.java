package fu.rms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.constant.Constant;
import fu.rms.constant.StatusConstant;
import fu.rms.constant.Utils;
import fu.rms.dto.OrderDishDto;
import fu.rms.dto.OrderDto;
import fu.rms.entity.Order;
import fu.rms.entity.OrderDish;
import fu.rms.mapper.OrderMapper;
import fu.rms.newDto.GetByDish;
import fu.rms.newDto.OrderDetail;
import fu.rms.newDto.OrderDishOptionDtoNew;
import fu.rms.repository.OrderDishOptionRepository;
import fu.rms.repository.OrderDishRepository;
import fu.rms.repository.OrderRepository;
import fu.rms.repository.StaffRepository;
import fu.rms.repository.TableRepository;
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
	TableRepository tableRepo;
	
	@Autowired
	OrderDishService orderDishService;
	
	@Autowired
	OrderDishOptionService orderDishOptionService;
	
	@Autowired
	OrderDishRepository orderDishRepo;
	
	@Autowired
	OrderDishOptionRepository orderDishOptionRepo;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

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
	@Transactional
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
				tableService.updateTableNewOrder(orderDto, StatusConstant.STATUS_TABLE_BUSY);
				//notifi for client when update table
				simpMessagingTemplate.convertAndSend("/topic/tables", tableService.getListTable());
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
	@Transactional
	public OrderDetail updateSaveOrder(OrderDto dto) {

		OrderDetail orderDetail = null;
		if(dto != null) {
			try {
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
			} catch (NullPointerException e) {
//				return Constant.RETURN_ERROR_NULL;
			}
		
			try {
				if(dto.getStatusId() == StatusConstant.STATUS_ORDER_ORDERING) {										// chưa order thì update trạng thái, ngày order
					Date orderDate = Utils.getCurrentTime();
					orderRepo.updateSaveOrder(StatusConstant.STATUS_ORDER_ORDERED, orderDate, dto.getTotalItem(), 
							dto.getTotalAmount(), dto.getComment(), dto.getOrderId());
					tableService.updateStatusOrdered(dto.getTableId(), StatusConstant.STATUS_TABLE_ORDERED);
				} else { 																							// nếu đã order rồi thì chỉ update số lượng và giá
					updateOrderQuantity(dto.getTotalItem(), dto.getTotalAmount(), dto.getOrderId());
				}
				simpMessagingTemplate.convertAndSend("/topic/tables", tableService.getListTable());
				orderDetail = getOrderById(dto.getOrderId());
			} catch (NullPointerException e) {
//				return Constant.RETURN_ERROR_NULL;
			}
			
		}
		return orderDetail;
	}
	
	/**
	 * thay đổi bàn
	 */
	@Override
	@Transactional
	public String changeOrderTable(OrderDto dto, Long tableId) {
		
		String result = "";
		int update = 0;
		try {
			if(dto != null) {
				Long statusTable = tableRepo.findStatusByTableId(tableId);
				if(statusTable == StatusConstant.STATUS_TABLE_ORDERED) {
					return Constant.TABLE_ORDERED;
				}else if(statusTable == StatusConstant.STATUS_TABLE_BUSY) {
					return Constant.TABLE_BUSY;
				}else {
					tableService.updateToReady(dto.getTableId(), StatusConstant.STATUS_TABLE_READY); 				// đổi bàn cũ thành trạng thái ready
					dto.setTableId(tableId);
					if(dto.getStatusId() == StatusConstant.STATUS_ORDER_ORDERING) {
						tableService.updateTableNewOrder(dto, StatusConstant.STATUS_TABLE_BUSY);									// đổi bàn mới thành trạng thái theo order đổi
					}else {
						tableService.updateTableNewOrder(dto, StatusConstant.STATUS_TABLE_ORDERED);
					}
					update = orderRepo.updateOrderTable(dto.getTableId(), dto.getModifiedBy(), Utils.getCurrentTime(), dto.getOrderId());
				}
				if (update == 1) {
					result = Constant.TABLE_READY;
				}else {
					result = Constant.TABLE_ERROR;
				}
			}
		} catch (Exception e) {
			return Constant.TABLE_ERROR;
		}
		return result;
	}

	/**
	 * hủy order
	 */
	@Override
	@Transactional
	public int updateCancelOrder(OrderDto dto) {
		int result = 0;
		if(dto != null) {
			try {
				if(dto.getStatusId() == StatusConstant.STATUS_ORDER_ORDERING) { 									// mới tạo order, chưa chọn món
					try {
						result = orderRepo.updateCancelOrder(StatusConstant.STATUS_ORDER_CANCELED, Utils.getCurrentTime(), "STAFF", dto.getComment(), dto.getOrderId());
					} catch (Exception e) {
						return Constant.RETURN_ERROR_NULL;
					}	
				}else if(dto.getStatusId() == StatusConstant.STATUS_ORDER_ORDERED) {								// 1 số chưa, 1 số đã
					List<OrderDish> listOrderDish = orderDishRepo.findOrderDishByOrder(dto.getOrderId());
					if(listOrderDish.size() != 0) {	
						for (OrderDish orderDish : listOrderDish) {
							// đã sử dụng nguyên vật liệu, chỉ canceled
							orderDishOptionRepo.updateCancelOrderDishOption(StatusConstant.STATUS_ORDER_DISH_OPTION_CANCELED, orderDish.getOrderDishId());
						}
						orderDishRepo.updateCancelOrderDishByOrder(StatusConstant.STATUS_ORDER_DISH_CANCELED, dto.getComment(), Utils.getCurrentTime(), "STAFF", dto.getOrderId());
					}
					result = orderRepo.updateCancelOrder(StatusConstant.STATUS_ORDER_CANCELED, Utils.getCurrentTime(), "STAFF", dto.getComment(), dto.getOrderId());
				}else {																								// đã sử dụng nguyên vật liệu, chỉ canceled, ko tính vào giá
					List<Long> listOrderDishId = orderDishRepo.getOrderDishId(dto.getOrderId());
					if(listOrderDishId.size() != 0) {
						for (Long orderDishId : listOrderDishId) {
							orderDishOptionRepo.updateCancelOrderDishOption(StatusConstant.STATUS_ORDER_DISH_OPTION_CANCELED, orderDishId);
						}
					}		
					orderDishRepo.updateCancelOrderDishByOrder(StatusConstant.STATUS_ORDER_DISH_CANCELED, dto.getComment(), Utils.getCurrentTime(), "STAFF", dto.getOrderId());
					result = orderRepo.updateCancelOrder(StatusConstant.STATUS_ORDER_CANCELED, Utils.getCurrentTime(), "STAFF", dto.getComment(), dto.getOrderId());
				}
				tableRepo.updateToReady(dto.getTableId(), StatusConstant.STATUS_TABLE_READY);
				simpMessagingTemplate.convertAndSend("/topic/tables", tableService.getListTable());
				
			} catch (NullPointerException e) {
				return Constant.RETURN_ERROR_NULL;
			}
		}
		
		return result;
	}


	/**
	 * bếp nhấn xác nhận đã nhân cả order: PREPARATION, bắt dầu nấu. Nếu status là COMPLETED thì là đã nấu xong
	 */
	@Override
	@Transactional
	public int updateOrderChef(OrderDto dto, Long statusId) {

		int result = 0;
		if(dto != null) {
			if(statusId == StatusConstant.STATUS_ORDER_PREPARATION && dto.getOrderDish().size() != 0) {
				for (OrderDishDto orderDish : dto.getOrderDish()) {
					orderDishService.updateStatusOrderDish(orderDish, StatusConstant.STATUS_ORDER_DISH_PREPARATION);
				}
			}
			if(statusId == StatusConstant.STATUS_ORDER_COMPLETED && dto.getOrderDish().size() != 0){
				for (OrderDishDto orderDish : dto.getOrderDish()){
					orderDishService.updateStatusOrderDish(orderDish, StatusConstant.STATUS_ORDER_DISH_COMPLETED);
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
	@Transactional
	public int updatePayOrder(OrderDto dto, Long statusId) {
		int result = 0;
		String timeToComplete = Utils.getOrderTime(Utils.getCurrentTime(), dto.getOrderDate());
		if(dto != null) {
			try {
				result = orderRepo.updatePayOrder(Utils.getCurrentTime(), dto.getCashierStaffId(), statusId, timeToComplete, dto.getOrderId());
			} catch (NullPointerException e) {
				return 0;
			}
			tableRepo.updateToReady(dto.getTableId(), StatusConstant.STATUS_TABLE_READY);
		}
		return result;
	}

	/**
	 * update về số lượng
	 */
	@Override
	@Transactional
	public int updateOrderQuantity(Integer totalItem, Double totalAmount, Long orderId) {
		int result = 0;
		try {
			result = orderRepo.updateOrderQuantity(totalItem, totalAmount, orderId);
		} catch (NullPointerException e) {
			return Constant.RETURN_ERROR_NULL;
		}
		
		return result;
	}

	/**
	 * select detail by id
	 */
	@Override
	public OrderDetail getOrderById(Long orderId) {
		Order entity= null;
		OrderDetail detail = null;
		try {
			entity = orderRepo.getOrderById(orderId);
			detail = orderMapper.entityToDetail(entity);
		} catch (NullPointerException e) {
			Constant.logger = LoggerFactory.getLogger(OrderService.class);
		}
		
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
	 * xác nhận bếp đã thực hiện xong món hoặc ordertaker trả món xong: ấn nguyên cả order
	 */
	@Override
	public int updateStatusOrder(OrderDto dto, Long statusId) {
		
		int result = 0;
//		if(dto != null) {
//			if(statusId == StatusConstant.STATUS_ORDER_JUST_COOKED && dto.getOrderDish().size() != 0){
//				for (OrderDishDto orderDish : dto.getOrderDish()) {
//					orderDishService.updateStatusOrderDish(orderDish, StatusConstant.STATUS_ORDER_DISH_JUST_COOKED);
//				}
//			}else 
//				if(statusId == StatusConstant.STATUS_ORDER_COMPLETED && dto.getOrderDish().size() != 0){
//				for (OrderDishDto orderDish : dto.getOrderDish()){
//					orderDishService.updateStatusOrderDish(orderDish, StatusConstant.STATUS_ORDER_DISH_COMPLETED);
//				}
//			} 
//			result = orderRepo.updateStatusOrder(statusId, dto.getOrderId());
//		}
		
		return result;
	}

	@Override
	public List<GetByDish> getByDish() {
		List<GetByDish> list = orderRepo.getByDish();
		return list;
	}

	@Override
	public int updateComment(OrderDto dto) {
		int result = 0;
		try {
			result = orderRepo.updateComment(dto.getComment(), dto.getOrderId());
		} catch (NullPointerException e) {
			return 0;
		}
		return result;
	}



}
