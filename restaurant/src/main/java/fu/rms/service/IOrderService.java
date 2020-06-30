package fu.rms.service;

import fu.rms.dto.OrderDto;
import fu.rms.entity.Order;

public interface IOrderService {

	OrderDto getCurrentOrderByTable(Long tableId);
	
	int insertOrder(OrderDto dto);
}
