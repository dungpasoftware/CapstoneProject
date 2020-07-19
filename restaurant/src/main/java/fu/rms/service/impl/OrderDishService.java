package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import fu.rms.constant.Constant;
import fu.rms.constant.StatusConstant;
import fu.rms.constant.Utils;
import fu.rms.dto.OrderDishDto;
import fu.rms.dto.OrderDto;
import fu.rms.entity.OrderDish;
import fu.rms.exception.DeleteException;
import fu.rms.exception.UpdateException;
import fu.rms.mapper.OrderDishMapper;
import fu.rms.newDto.mapper.OrderDishOptionMapper;
import fu.rms.newDto.OrderDishOptionDtoNew;
import fu.rms.newDto.SumQuantityAndPrice;
import fu.rms.repository.OrderDishOptionRepository;
import fu.rms.repository.OrderDishRepository;
import fu.rms.service.IOrderDishService;

@Service
public class OrderDishService implements IOrderDishService {
	
	@Autowired
	OrderDishMapper orderDishMapper;
	
	@Autowired
	OrderDishRepository orderDishRepo;
	
	@Autowired
	OrderDishOptionMapper orderDishOptionMapper;
	
	@Autowired
	OrderDishOptionService orderDishOptionService;
	
	@Autowired
	OrderDishOptionRepository orderDishOptionRepo;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;


	/**
	 * danh sách món ăn trong order
	 */
	@Override
	public List<OrderDishDto> getListOrderDishByOrder(Long orderId) {

		List<OrderDish> listOrderDish = orderDishRepo.findOrderDishByOrder(orderId);
		List<OrderDishDto> listDto = listOrderDish.stream().map(orderDishMapper::entityToDto)
				.collect(Collectors.toList());	
		
		for (int i = 0; i < listOrderDish.size(); i++) {
			List<OrderDishOptionDtoNew> listOrderDishOption = new ArrayList<OrderDishOptionDtoNew>();
			if(listDto.get(i).getOrderDishOptions() != null && listDto.get(i).getOrderDishOptions().size() != 0) {
				
				listOrderDishOption = listOrderDish.get(i).getOrderDishOptions()
				.stream().map(orderDishOptionMapper::entityToDto).collect(Collectors.toList());	;
			}
			listDto.get(i).setOrderDishOptions(listOrderDishOption);
		}
		
		return listDto;
	}

	/**
	 * thêm món khi order
	 */
	@Override
	public Long insertOrderDish(OrderDishDto dto, Long orderId) {

		int result =  0;
		Long orderDishId = (long) 0;
		if(dto != null) {
			result = orderDishRepo.insertOrderDish(orderId, dto.getDish().getDishId(), dto.getComment(),
					dto.getQuantity(), dto.getSellPrice(), dto.getSumPrice(), // sumPrice
					StatusConstant.STATUS_ORDER_DISH_ORDERED);
		}
		if(result == 1) {
			orderDishId = orderDishRepo.getLastestOrderDishId(orderId);
		}
		return orderDishId;
	}

	/**
	 * bếp ấn nấu xong trả lần lượt, nếu trả hết rồi thì trạng thái order cũng thay đổi
	 */
	@Override
	public int updateStatusOrderDish(OrderDishDto dto, Long statusId) {
		int result = 0;
		try {
			result = orderDishRepo.updateStatusOrderDish(statusId, dto.getOrderDishId());
			int count = 0;
			if(result == 1 && statusId == StatusConstant.STATUS_ORDER_DISH_COMPLETED) {
				count = getCountCompleteOrder(dto.getOrderOrderId());
				if(count == 0) {
					OrderDto orderDto = new OrderDto();
					orderDto.setOrderId(dto.getOrderOrderId());
					orderService.updateStatusOrder(orderDto, StatusConstant.STATUS_ORDER_COMPLETED);
				}
			}
		} catch (NullPointerException e) {
			return Constant.RETURN_ERROR_NULL;
		}
		
		return result;
	}

	/**
	 * cập nhật order: order dish: giá, số lượng
	 */
	@Override
	public int updateQuantityOrderDish(OrderDishDto dto) {
		int result = 0;
		if(dto!= null) {
			try {
				result = orderDishRepo.updateQuantityOrderDish(dto.getComment(), dto.getQuantity(), dto.getSellPrice(), dto.getSumPrice(),
						StatusConstant.STATUS_ORDER_DISH_ORDERED, dto.getOrderDishId());
				if(result == 1) { // cập nhật lại order
					SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());
					result = orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
				}
				simpMessagingTemplate.convertAndSend("/topic/orderdetail/"+dto.getOrderOrderId(), orderService.getOrderById(dto.getOrderOrderId()));
			} catch (NullPointerException e) {
				return Constant.RETURN_ERROR_NULL;
			}
		}
		return result;
	}

	/**
	 * select sum quantity and price by order
	 */
	@Override
	public SumQuantityAndPrice getSumQtyAndPriceByOrder(Long orderId) {
		SumQuantityAndPrice sum = orderDishRepo.getSumQtyAndPrice(orderId, StatusConstant.STATUS_ORDER_DISH_CANCELED);
		return sum;
	}

	/**
	 * cập nhật lại topping
	 */
	@Override
	public int updateToppingCommentOrderDish(OrderDishDto dto) {
		int result = 0;
		try {
			if(dto!= null && dto.getOrderDishOptions().size() != 0) {
				
				for (OrderDishOptionDtoNew orderDishOption : dto.getOrderDishOptions()) {
					if(orderDishOption.getOrderDishOptionId() == 999999999) {
						orderDishOptionService.insertOrderDishOption(orderDishOption, dto.getOrderDishId());
					}else {
						orderDishOptionService.updateQuantityOrderDishOption(orderDishOption);
					}
				}
				result = updateQuantityOrderDish(dto);
				
			}
		} catch (Exception e) {
			return Constant.RETURN_ERROR_NULL;
		}
		
		return result;
	}

	/**
	 * select by id
	 */
	@Override
	public OrderDishDto getOrderDishById(Long orderDishId) {
		OrderDishDto dto = null;
		if(orderDishId != null) {
			OrderDish entity = orderDishRepo.findOrderDishById(orderDishId);
			if(entity != null) {
				dto = orderDishMapper.entityToDto(entity);
			}
		}
		return dto;
	}

	/**
	 * thay đổi nếu cancel món trong order
	 */
	@Override
	public int updateCancelOrderDish(OrderDishDto dto) {
		int result = 0;
		try {
			if(dto.getStatusStatusId() == StatusConstant.STATUS_ORDER_DISH_ORDERED) {								// chưa xử dụng nvl, xóa luôn
				try {
					orderDishOptionRepo.deleteOrderDishOption(dto.getOrderDishId());
					result = orderDishRepo.deleteOrderDish(dto.getOrderDishId());
				} catch (Exception e) {
					throw new DeleteException("Xóa món ăn thất bại");
				}
			}else {
				try {
					OrderDishDto odDto = getOrderDishById(dto.getOrderDishId());
					if(odDto.getStatusStatusId() == StatusConstant.STATUS_ORDER_DISH_OK_CANCEL) {					// lần thứ 2,3,4,,... vào cancel
						if(dto.getQuantityCancel() == odDto.getQuantityOk()) {										// nếu số lương hủy = số lượng ok còn lại sau lần hủy đầu
							orderDishOptionRepo.updateCancelOrderDishOption(StatusConstant.STATUS_ORDER_DISH_OPTION_CANCELED, dto.getOrderDishId());
							dto.setQuantityOk(0);						// tính lại số lượng Ok
							dto.setQuantityCancel(odDto.getQuantityCancel() + dto.getQuantityCancel()); 			// tổng số lượng cancel sau các lần cancel
							result = orderDishRepo.updateCancelOrderDish(StatusConstant.STATUS_ORDER_DISH_CANCELED, dto.getComment(), 		// cập nhât lại tổng giá
									dto.getQuantityCancel(), dto.getQuantityOk(), dto.getQuantityOk() * odDto.getSellPrice(), Utils.getCurrentTime(), "STAFF", dto.getOrderDishId());
						}else {																						
							dto.setQuantityOk(odDto.getQuantityOk() - dto.getQuantityCancel());						// tính lại số lượng Ok
							dto.setQuantityCancel(odDto.getQuantityCancel() + dto.getQuantityCancel()); 			// tổng số lượng cancel sau các lần cancel
							result = orderDishRepo.updateCancelOrderDish(StatusConstant.STATUS_ORDER_DISH_OK_CANCEL, dto.getComment(), 		// cập nhât lại tổng giá
									dto.getQuantityCancel(), dto.getQuantityOk(), dto.getQuantityOk() * odDto.getSellPrice(), Utils.getCurrentTime(), "STAFF", dto.getOrderDishId());
						}
					}else {																							//lân đầu hủy món
						if(dto.getQuantityCancel() == dto.getQuantity()) {											// hủy phát hết luôn
							orderDishOptionRepo.updateCancelOrderDishOption(StatusConstant.STATUS_ORDER_DISH_OPTION_CANCELED, dto.getOrderDishId());
							dto.setQuantityOk(odDto.getQuantity() - dto.getQuantityCancel());						// = 0
							result = orderDishRepo.updateCancelOrderDish(StatusConstant.STATUS_ORDER_DISH_CANCELED, dto.getComment(), 		// cancel hết thì tổng giá về 0
									dto.getQuantityCancel(), dto.getQuantityOk(), dto.getQuantityOk() * odDto.getSellPrice(), Utils.getCurrentTime(), "STAFF", dto.getOrderDishId());
						}else {																												// hủy 1 số
							dto.setQuantityOk(odDto.getQuantity() - dto.getQuantityCancel());
							result = orderDishRepo.updateCancelOrderDish(StatusConstant.STATUS_ORDER_DISH_OK_CANCEL, dto.getComment(), 		// cập nhât lại tổng giá
									dto.getQuantityCancel(), dto.getQuantityOk(), dto.getQuantityOk() * odDto.getSellPrice(), Utils.getCurrentTime(), "STAFF", dto.getOrderDishId());
						}
						
					}

				} catch (Exception e) {
					throw new UpdateException("Hủy món ăn thất bại");
				}
			}
			if(result == 1) { 																// cập nhật lại số lượng và giá trong order
				SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());
				result = orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
				simpMessagingTemplate.convertAndSend("/topic/orderdetail/"+dto.getOrderOrderId(), orderService.getOrderById(dto.getOrderOrderId()));
				
			}
		} catch (NullPointerException e) {
			return Constant.RETURN_ERROR_NULL;
		}
		
		return result;
	}

	
	/**
	 * đếm số món chưa complete
	 */
	@Override
	public int getCountCompleteOrder(Long orderId) {
		int count = 0;
		if(orderId != null) {
			count = orderDishRepo.getCountCompleteOrder(orderId, StatusConstant.STATUS_ORDER_DISH_COMPLETED, StatusConstant.STATUS_ORDER_CANCELED);
		}
		return count;
	}


}
