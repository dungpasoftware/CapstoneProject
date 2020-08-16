package fu.rms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.exception.NullPointerException;
import fu.rms.newDto.OrderDishOptionDto;
import fu.rms.repository.OrderDishOptionRepository;
import fu.rms.service.IOrderDishOptionService;

@Service
public class OrderDishOptionService implements IOrderDishOptionService{

	@Autowired
	OrderDishOptionRepository orderDishOptionRepo;
	
	@Override
	public int insertOrderDishOption(OrderDishOptionDto dto, Long orderDishId) {
		int result = 0;
		try {
			result = orderDishOptionRepo.insertOrderDishOption(orderDishId, dto.getOptionId(), dto.getQuantity(),
				dto.getSumPrice(), dto.getOptionPrice(), StatusConstant.STATUS_ORDER_DISH_OPTION_DONE);
		
		} catch (NullPointerException e) {
			throw new NullPointerException("Có gì đó không đúng xảy ra");
		}
		return result;
	}

	@Override
	public int updateQuantityOrderDishOption(OrderDishOptionDto dto) {
		int result = 0;
		try {
			if(dto.getOrderDishOptionId()!= null) {
				result = orderDishOptionRepo.updateOrderDishOption(dto.getOptionId(), dto.getQuantity(), dto.getOptionPrice(), 
						dto.getSumPrice(), StatusConstant.STATUS_ORDER_DISH_OPTION_DONE, dto.getOrderDishOptionId());
			}
			
		} catch (NullPointerException e) {
			throw new NullPointerException("Có gì đó không đúng xảy ra");
		}
		return result;
	}

	@Override
	public int updateCancelOrderDishOption(Long orderDishId, Long statusId) {
		int result = 0;
		try {
			if(orderDishId != null) {
				result = orderDishOptionRepo.updateCancelOrderDishOption(statusId, orderDishId);
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("Có gì đó không đúng xảy ra");
		}
		return result;
	}

	@Override
	public int deleteOrderDishOption(Long orderDishId) {
		int result = 0;
		try {
			if(orderDishId != null) {
				result = orderDishOptionRepo.deleteOrderDishOption(orderDishId);
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("Có gì đó không đúng xảy ra");
		}
		return result;
	}

}
