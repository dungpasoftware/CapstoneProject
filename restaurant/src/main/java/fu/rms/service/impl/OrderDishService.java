package fu.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.OrderDishDto;
import fu.rms.entity.OrderDish;
import fu.rms.mapper.OrderDishMapper;
import fu.rms.repository.OrderDishRepository;
import fu.rms.service.IOrderDishService;

@Service
public class OrderDishService implements IOrderDishService {
	
	@Autowired
	OrderDishMapper orderDishMapper;
	
	@Autowired
	OrderDishRepository orderDishRepo;

	@Override
	public List<OrderDishDto> getListOrderDishByOrder(Long orderId) {

//		List<OrderDish> listOrderDish = orderDishRepo.findByOrderId(Long orderId);
		
		return null;
	}

}
