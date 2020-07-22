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
import fu.rms.dto.OrderDishCancelDto;
import fu.rms.dto.OrderDishDto;
import fu.rms.dto.OrderDto;
import fu.rms.entity.OrderDish;
import fu.rms.entity.Status;
import fu.rms.exception.NotFoundException;
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
	OrderDishCancelService orderDishCancelService;
	
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
					dto.getQuantity(), 0, dto.getQuantity(), dto.getSellPrice(), dto.getSumPrice(), "STAFF", Utils.getCurrentTime(),
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
	public String updateQuantityOrderDish(OrderDishDto dto) {
		int result = 0;
		if(dto!= null) {
			try {
				int addQuantity = 0;
				OrderDishDto orderDishDto = getOrderDishById(dto.getOrderDishId());
				if(orderDishDto.getStatusStatusId() == StatusConstant.STATUS_ORDER_DISH_ORDERED) {	// nếu là ordered tăng giảm thoải mái
					
					result = orderDishRepo.updateQuantityOrderDish(dto.getQuantityOk(), dto.getQuantityOk(), dto.getSellPrice(), dto.getSumPrice(), dto.getOrderDishId());	
					
				} else if (orderDishDto.getStatusStatusId() == StatusConstant.STATUS_ORDER_DISH_COMPLETED 
						|| orderDishDto.getStatusStatusId() == StatusConstant.STATUS_ORDER_DISH_PREPARATION) {	// nếu là completed hoặc prepare thì chỉ tăng số lượng (insert thêm lượng chênh)

					if(orderDishDto.getQuantityOk() >= dto.getQuantityOk()) {
						return Constant.INPUT_WRONG;
					}else {
						addQuantity = dto.getQuantityOk() - orderDishDto.getQuantityOk();						// tăng chỗ cộng thêm
						result = orderDishRepo.insertOrderDish(orderDishDto.getOrderOrderId(), orderDishDto.getDish().getDishId(), orderDishDto.getComment(),	//insert mới, theo quantity lệch
								addQuantity, 0, addQuantity, dto.getSellPrice(), dto.getSellPrice()*addQuantity, "STAFF", Utils.getCurrentTime(),
								StatusConstant.STATUS_ORDER_DISH_ORDERED);
					}
					Long orderDishId = (long) 0;
					if(result == 1) {																			// insert orderdish thành công
						orderDishId = orderDishRepo.getLastestOrderDishId(orderDishDto.getOrderOrderId());
					}else {
						return Constant.CHANGE_ERROR;
					}
					if(orderDishDto.getOrderDishOptions().size() > 0) {											// check xem món đó đang có option ko?
						for (OrderDishOptionDtoNew dishOption : orderDishDto.getOrderDishOptions()) {
							orderDishOptionRepo.insertOrderDishOption(orderDishId, dishOption.getOptionId(), dishOption.getQuantity(),
									dishOption.getSumPrice(), dishOption.getOptionPrice(), StatusConstant.STATUS_ORDER_DISH_OPTION_DONE);
						}
					}
				}
				
				if(result == 1) { 												// cập nhật lại order
					SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());
					result = orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
				}
				simpMessagingTemplate.convertAndSend("/topic/orderdetail/"+dto.getOrderOrderId(), orderService.getOrderById(dto.getOrderOrderId()));
			} catch (NullPointerException e) {
				return Constant.NULL;
			}
		}
		return Constant.CHANGE_SUCCESS;
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
				
				for (OrderDishOptionDtoNew orderDishOption : dto.getOrderDishOptions()) {								// nếu mà có topping thì hoặc là update topping, hoặc là thêm topping mới
					if(orderDishOption.getOrderDishOptionId() == 999999999 && orderDishOption.getQuantity() > 0) {		// nếu id gửi về là 99999999 và quantity > 0 thì là insert mới
						orderDishOptionService.insertOrderDishOption(orderDishOption, dto.getOrderDishId());
					}else if(orderDishOption.getOrderDishOptionId() == 999999999){										// nếu id gửi về là 99999999 thì ko làm gì cả
					}else {																								// nếu id gửi về ko phải là 99999999, tức là update cái topping cũ
						if(orderDishOption.getQuantity() == 0) {														// nếu cho về quantity = 0 thì xóa nó đi
							orderDishOptionRepo.deleteOrderDishOptionById(orderDishOption.getOrderDishOptionId());
						}else {																							// nếu ko thì update
							orderDishOptionService.updateQuantityOrderDishOption(orderDishOption);
						}
					}
				}
				result = orderDishRepo.updateToppingComment(dto.getComment(), dto.getSellPrice(), dto.getSumPrice(), dto.getOrderDishId());	// xong thì cập nhật lại comment và giá
				if(result == 1) { 																						// cập nhật lại order
					SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());
					result = orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
				}
			}else if(dto!= null && dto.getOrderDishOptions().size() == 0) {												// nếu ko gửi topping về thì là chỉ comment
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
	public String updateCancelOrderDish(OrderDishDto dto) {
		try {
			
			OrderDish orderDish=orderDishRepo.findById(dto.getOrderDishId()).get();
			OrderDishCancelDto orderDishCancelDto = new OrderDishCancelDto();
			if(orderDish.getStatus().getStatusId() == StatusConstant.STATUS_ORDER_DISH_ORDERED
					|| orderDish.getStatus().getStatusId() == StatusConstant.STATUS_ORDER_DISH_CANCELED) {
				return Constant.STATUS_NOT_CHANGE;
			}
			if(orderDish.getQuantityOk() == null) {	
				return Constant.NO_DATA;
			}else {
				if(orderDish.getQuantityOk() != orderDish.getQuantity()) {									// lần thứ 2,3.. hủy món
					if(dto.getQuantityCancel() == orderDish.getQuantityOk()) {								// hủy hết
						if(orderDish.getOrderDishOptions().size() != 0) {
							orderDishOptionRepo.updateCancelOrderDishOption(StatusConstant.STATUS_ORDER_DISH_OPTION_CANCELED, dto.getOrderDishId());
						}
						orderDishCancelDto = new OrderDishCancelDto(null, dto.getQuantityCancel(), dto.getCommentCancel(), "STAFF", Utils.getCurrentTime(), dto.getOrderDishId());
						try {
							orderDishCancelService.insertCancel(orderDishCancelDto);						// thay đổi thì thêm bản ghi vào bảng cancel
						} catch (Exception e) {
							return Constant.STATUS_NOT_CHANGE;
						}
						dto.setQuantityOk(0);
						dto.setQuantityCancel(orderDish.getQuantity()); 									// hủy hết rồi thì = số lượng quantity ban đầu
						dto.setSumPrice(dto.getQuantityOk()*orderDish.getSellPrice());						// set lại tổng giá = 0
						
					}else if(dto.getQuantityCancel() < orderDish.getQuantityOk()) {							// hủy thêm 1 số
						orderDishCancelDto = new OrderDishCancelDto(null, dto.getQuantityCancel(), dto.getCommentCancel(), "STAFF", Utils.getCurrentTime(), dto.getOrderDishId());
						try {
							orderDishCancelService.insertCancel(orderDishCancelDto);						// thay đổi thì thêm bản ghi vào bảng cancel
						} catch (Exception e) {
							return Constant.STATUS_NOT_CHANGE;
						}
						dto.setQuantityOk(orderDish.getQuantityOk() - dto.getQuantityCancel());
						dto.setQuantityCancel(orderDish.getQuantityCancel() + dto.getQuantityCancel());
						dto.setSumPrice(dto.getQuantityOk()*orderDish.getSellPrice());
					}else {
						return Constant.CANCEL_NOT_MORE_THAN_OK;
					}
				}else {																						// lần đầu hủy món	
					if(dto.getQuantityCancel() == orderDish.getQuantityOk()) {								// hủy hết số còn lại
						if(dto.getOrderDishOptions().size() != 0) {
							orderDishOptionRepo.updateCancelOrderDishOption(StatusConstant.STATUS_ORDER_DISH_OPTION_CANCELED, dto.getOrderDishId());
						}
						orderDishCancelDto = new OrderDishCancelDto(null, dto.getQuantityCancel(), dto.getCommentCancel(), "STAFF", Utils.getCurrentTime(), dto.getOrderDishId());
						try {
							orderDishCancelService.insertCancel(orderDishCancelDto);						// thay đổi thì thêm bản ghi vào bảng cancel
						} catch (Exception e) {
							return Constant.STATUS_NOT_CHANGE;
						}
						dto.setQuantityOk(0);
						dto.setQuantityCancel(orderDish.getQuantity());										// tổng số quantity gọi ban đầu
						dto.setSumPrice(dto.getQuantityOk()*orderDish.getSellPrice());						// tính lại tổng giá	
					}else if(dto.getQuantityCancel() < orderDish.getQuantityOk()) {
						orderDishCancelDto = new OrderDishCancelDto(null, dto.getQuantityCancel(), dto.getCommentCancel(), "STAFF", Utils.getCurrentTime(), dto.getOrderDishId());
						try {
							orderDishCancelService.insertCancel(orderDishCancelDto);						// thay đổi thì thêm bản ghi vào bảng cancel
						} catch (Exception e) {
							return Constant.STATUS_NOT_CHANGE;
						}
						dto.setQuantityOk(orderDish.getQuantityOk() - dto.getQuantityCancel());
						dto.setQuantityCancel(dto.getQuantityCancel() + orderDish.getQuantityCancel());
						dto.setSumPrice(dto.getQuantityOk()*orderDish.getSellPrice());
					}else {
						return Constant.CANCEL_NOT_MORE_THAN_OK;
					}
				}
			}
			orderDish.setQuantityOk(dto.getQuantityOk());
			orderDish.setSumPrice(dto.getSumPrice());
			orderDish.setQuantityCancel(dto.getQuantityCancel());
			orderDish.setModifiedBy("STAFF");
			orderDish.setModifiedDate(Utils.getCurrentTime());
			if(dto.getQuantityCancel() == orderDish.getQuantity()) {
				Status status = statusRepo.findById(StatusConstant.STATUS_ORDER_DISH_CANCELED)
						.orElseThrow(()-> new NotFoundException("Not found Status: "+StatusConstant.STATUS_ORDER_DISH_CANCELED));
				orderDish.setStatus(status);
			}else {
				Status status = statusRepo.findById(orderDish.getStatus().getStatusId()).get();
				orderDish.setStatus(status);
			}
			orderDishRepo.save(orderDish);
			
			SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());								// cập nhật lại số lượng và giá trong order
			orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
			
			simpMessagingTemplate.convertAndSend("/topic/orderdetail/"+dto.getOrderOrderId(), orderService.getOrderById(dto.getOrderOrderId()));		// socket
				
		} catch (NullPointerException e) {
			return Constant.NULL;
		}
		
		return Constant.CHANGE_SUCCESS;
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
			
			for (int i = 0; i < listOrderDish.size(); i++) {														// topping
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
