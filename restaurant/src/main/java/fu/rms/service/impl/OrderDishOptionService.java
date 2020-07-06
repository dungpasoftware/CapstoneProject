package fu.rms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.newDto.OrderDishOptionDtoNew;
import fu.rms.repository.OrderDishOptionRepository;
import fu.rms.service.IOrderDishOptionService;

@Service
public class OrderDishOptionService implements IOrderDishOptionService{

	@Autowired
	OrderDishOptionRepository orderDishOptionRepo;
	
	@Override
	public int insertOrderDishOption(OrderDishOptionDtoNew dto, Long orderDishId) {
		int result = 0;
		result = orderDishOptionRepo.insertOrderDishOption(orderDishId, dto.getOptionId(), dto.getQuantity(),
				dto.getSumPrice(), dto.getOptionPrice(), StatusConstant.STATUS_ORDER_DISH_OPTION_DONE);
		return result;
	}

}
