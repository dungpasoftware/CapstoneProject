package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.OrderDishDto;
import fu.rms.dto.OrderDto;
import fu.rms.entity.OrderDish;
import fu.rms.mapper.OrderDishMapper;
import fu.rms.newDto.mapper.OrderDishOptionMapper;
import fu.rms.newDto.OrderDishOptionDtoNew;
import fu.rms.newDto.SumQuantityAndPrice;
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
	OrderService orderService;


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

	/*
	 * thêm món khi order
	 */
	@Override
	public int insertOrderDish(OrderDishDto dto, Long orderId) {

		int result =  0;
		if(dto != null) {
			result = orderDishRepo.insertOrderDish(orderId, dto.getDish().getDishId(),
					dto.getQuantity(), dto.getSellPrice(), dto.getSumPrice(),
					StatusConstant.STATUS_ORDER_DISH_ORDERED);
		}
		return result;

	}

	/**
	 * trả món, trả lần lượt, nếu trả hết rồi thì trạng thái order cũng thay đổi
	 */
	@Override
	public int updateStatusOrderDish(OrderDishDto dto, Long statusId) {
		int result = 0;
		result = orderDishRepo.updateStatusOrderDish(statusId, dto.getOrderDishId());
		int count = 0;
		if(result == 1 && statusId == StatusConstant.STATUS_ORDER_DISH_COMPLETED) {
			count = getCountCompleteOrder(dto.getOrderOrderId());
			OrderDto orderDto = new OrderDto();
			orderDto.setStatusId(StatusConstant.STATUS_ORDER_COMPLETED);
			if(count == 0) {
				orderService.updateStatusOrder(orderDto, statusId);
			}
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
			result = orderDishRepo.updateQuantityOrderDish(dto.getQuantity(), dto.getSellPrice(), dto.getSumPrice(),
					StatusConstant.STATUS_ORDER_DISH_ORDERED, dto.getOrderDishId());
		}
		if(result == 1) { // cập nhật lại order
			SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());
			result = orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
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
	public int updateToppingOrderDish(OrderDishDto dto) {
		int result = 0;
		if(dto!= null && dto.getOrderDishOptions().size() != 0) {
			for (OrderDishOptionDtoNew orderDishOption : dto.getOrderDishOptions()) {
				orderDishOptionService.insertOrderDishOption(orderDishOption, dto.getOrderDishId());
			}
			result = updateQuantityOrderDish(dto);
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
		int result = updateStatusOrderDish(dto, StatusConstant.STATUS_ORDER_CANCELED);
		if(result == 1) { // cập nhật lại order
			SumQuantityAndPrice sum = getSumQtyAndPriceByOrder(dto.getOrderOrderId());
			result = orderService.updateOrderQuantity(sum.getSumQuantity(), sum.getSumPrice(), dto.getOrderOrderId());
		}
		return result;
	}

	
	/*
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
