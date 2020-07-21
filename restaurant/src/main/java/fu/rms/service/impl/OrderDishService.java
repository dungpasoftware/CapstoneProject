package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.constant.Constant;
import fu.rms.constant.StatusConstant;
import fu.rms.constant.Utils;
import fu.rms.dto.OrderDishDto;
import fu.rms.dto.OrderDto;
import fu.rms.entity.OrderDish;
import fu.rms.entity.Status;
import fu.rms.mapper.OrderDishMapper;
import fu.rms.newDto.mapper.OrderDishOptionMapper;
import fu.rms.newDto.OrderDishOptionDtoNew;
import fu.rms.newDto.SumQuantityAndPrice;
import fu.rms.repository.OrderDishOptionRepository;
import fu.rms.repository.OrderDishRepository;
import fu.rms.repository.StatusRepository;
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
	
	@Autowired
	private StatusRepository statusRepo;


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
	@Transactional
	public Long insertOrderDish(OrderDishDto dto, Long orderId) {

		int result =  0;
		Long orderDishId = (long) 0;
		if(dto != null) {
			result = orderDishRepo.insertOrderDish(orderId, dto.getDish().getDishId(), dto.getComment(),
					dto.getQuantity(), dto.getSellPrice(), dto.getSumPrice(), "STAFF", Utils.getCurrentTime(),
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
	@Transactional
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
	@Transactional
	public int updateQuantityOrderDish(OrderDishDto dto) {
		int result = 0;
		if(dto!= null) {
			try {
				result = orderDishRepo.updateQuantityOrderDish(dto.getQuantity(), dto.getSellPrice(), dto.getSumPrice(), dto.getOrderDishId());
				if(result == 1) { 												// cập nhật lại order
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
	@Transactional
	public int updateToppingCommentOrderDish(OrderDishDto dto) {
		int result = 0;
		try {
			if(dto!= null && dto.getOrderDishOptions().size() != 0) {
				
				for (OrderDishOptionDtoNew orderDishOption : dto.getOrderDishOptions()) {
					if(orderDishOption.getOrderDishOptionId() == 999999999 && orderDishOption.getQuantity() > 0) {
						orderDishOptionService.insertOrderDishOption(orderDishOption, dto.getOrderDishId());
					}else if(orderDishOption.getOrderDishOptionId() == 999999999){
					}else {
						if(orderDishOption.getQuantity() == 0) {
							orderDishOptionRepo.deleteOrderDishOptionById(orderDishOption.getOrderDishOptionId());
						}else {
							orderDishOptionService.updateQuantityOrderDishOption(orderDishOption);
						}
					}
				}
				result = orderDishRepo.updateToppingComment(dto.getComment(), dto.getSellPrice(), dto.getSumPrice(), dto.getOrderDishId());
				if(result == 1) { 												// cập nhật lại order
					SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());
					result = orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
				}
			}else if(dto!= null && dto.getOrderDishOptions().size() == 0) {
				result = orderDishRepo.updateCommentOrderDish(dto.getComment(), dto.getOrderDishId());
			}
			simpMessagingTemplate.convertAndSend("/topic/orderdetail/"+dto.getOrderOrderId(), orderService.getOrderById(dto.getOrderOrderId()));
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
	@Transactional
	public int updateCancelOrderDish(OrderDishDto dto) {
		int result = 0;
		try {
			
			OrderDish orderDish=orderDishRepo.findById(dto.getOrderDishId()).get();
			if(orderDish.getStatus().getStatusId() == StatusConstant.STATUS_ORDER_DISH_OK_CANCEL) {					// lần thứ 2,3,4,,... vào cancel
				if(dto.getQuantityCancel() == orderDish.getQuantityOk()) {											// nếu số lương hủy = số lượng ok còn lại sau lần hủy đầu
					orderDishOptionRepo.updateCancelOrderDishOption(StatusConstant.STATUS_ORDER_DISH_OPTION_CANCELED, dto.getOrderDishId());
					dto.setQuantityOk(0);																			// tính lại số lượng Ok
					dto.setQuantityCancel(orderDish.getQuantityCancel() + dto.getQuantityCancel()); 				// tổng số lượng cancel sau các lần cancel
				}else if(dto.getQuantityCancel() > orderDish.getQuantityOk()){
					return Constant.RETURN_ERROR_NULL;
				} else{																						
					dto.setQuantityOk(orderDish.getQuantityOk() - dto.getQuantityCancel());							// tính lại số lượng Ok
					dto.setQuantityCancel(orderDish.getQuantityCancel() + dto.getQuantityCancel()); 				// tổng số lượng cancel sau các lần cancel
				}
			}else if(orderDish.getStatus().getStatusId() == StatusConstant.STATUS_ORDER_DISH_PREPARATION || orderDish.getStatus().getStatusId() == StatusConstant.STATUS_ORDER_DISH_COMPLETED) {// lần đầu cancel món này																			//lân đầu hủy món
				if(dto.getQuantityCancel() == orderDish.getQuantity()) {											// hủy phát hết luôn
					orderDishOptionRepo.updateCancelOrderDishOption(StatusConstant.STATUS_ORDER_DISH_OPTION_CANCELED, dto.getOrderDishId());
					dto.setQuantityOk(0);																			// = 0
				} else if(dto.getQuantityCancel() > orderDish.getQuantity()){
					return Constant.RETURN_ERROR_NULL;
				} else {																							// hủy 1 số
					dto.setQuantityOk(orderDish.getQuantity() - dto.getQuantityCancel());							// lần đầu cancel
				}
			}else if(orderDish.getStatus().getStatusId() == StatusConstant.STATUS_ORDER_DISH_ORDERED || orderDish.getStatus().getStatusId() == StatusConstant.STATUS_ORDER_DISH_CANCELED) {
				return Constant.RETURN_ERROR_NULL;
			}
			
			orderDish.setCommentCancel(dto.getCommentCancel());
			orderDish.setQuantityOk(dto.getQuantityOk());
			orderDish.setSumPrice(dto.getQuantityOk()*orderDish.getSellPrice());
			orderDish.setQuantityCancel(dto.getQuantityCancel());
			orderDish.setModifiedBy("STAFF");
			orderDish.setModifiedDate(Utils.getCurrentTime());
			if(dto.getQuantityCancel() == orderDish.getQuantity()) {
				Status status = statusRepo.findById(StatusConstant.STATUS_ORDER_DISH_CANCELED).get();
				orderDish.setStatus(status);
				orderDish.setOrderDishId(dto.getOrderDishId());
			}else {
				Status status = statusRepo.findById(StatusConstant.STATUS_ORDER_DISH_OK_CANCEL).get();
				orderDish.setStatus(status);
				orderDish.setOrderDishId(dto.getOrderDishId());
			}
			orderDishRepo.save(orderDish);
			
			SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());								// cập nhật lại số lượng và giá trong order
			result = orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
			
			simpMessagingTemplate.convertAndSend("/topic/orderdetail/"+dto.getOrderOrderId(), orderService.getOrderById(dto.getOrderOrderId()));		// socket
				
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
		Integer count = 0;
		if(orderId != null) {
			count = orderDishRepo.getCountCompleteOrder(orderId, StatusConstant.STATUS_ORDER_DISH_COMPLETED, StatusConstant.STATUS_ORDER_DISH_CANCELED, StatusConstant.STATUS_ORDER_DISH_OK_CANCEL);
			if(count == null) {
				count = 0;
			}	
		}
		return count;
	}
	
	/**
	 * hiển thị danh sách lên màn hình trả món, chỉ hiển thị các món đã hoàn thành
	 */
	@Override
	public List<OrderDishDto> getCanReturnByOrderId(Long orderId) {
		
		List<OrderDishDto> listDto = null;
		if(orderId != null) {
			List<OrderDish> listOrderDish = orderDishRepo.getCanReturnByOrderId(StatusConstant.STATUS_ORDER_DISH_COMPLETED, orderId);
			listDto = listOrderDish.stream().map(orderDishMapper::entityToDto)
					.collect(Collectors.toList());	
			
			for (int i = 0; i < listOrderDish.size(); i++) {
				List<OrderDishOptionDtoNew> listOrderDishOption = new ArrayList<OrderDishOptionDtoNew>();
				if(listDto.get(i).getOrderDishOptions() != null && listDto.get(i).getOrderDishOptions().size() != 0) {
					
					listOrderDishOption = listOrderDish.get(i).getOrderDishOptions()
					.stream().map(orderDishOptionMapper::entityToDto).collect(Collectors.toList());	;
				}
				listDto.get(i).setOrderDishOptions(listOrderDishOption);
			}
		}
		return listDto;
	}


}
