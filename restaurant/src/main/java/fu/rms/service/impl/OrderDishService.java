package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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

		List<OrderDish> listOrderDish = orderDishRepo.getOrderDishByOrder(orderId);
		List<OrderDishDto> listDto = listOrderDish.stream().map(orderDishMapper::entityToDto)
				.collect(Collectors.toList());	
		return listDto;
	}

	@Override
	public int insertOrderDish(OrderDishDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateStatusOrderDish(Long status, Long orderDishId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateQuantityOrderDish(OrderDishDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}

}
