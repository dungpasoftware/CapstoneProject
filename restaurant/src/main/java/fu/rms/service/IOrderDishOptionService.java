package fu.rms.service;

import fu.rms.newDto.OrderDishOptionDtoNew;

public interface IOrderDishOptionService {

	int insertOrderDishOption(OrderDishOptionDtoNew dto, Long orderDishId);
}
