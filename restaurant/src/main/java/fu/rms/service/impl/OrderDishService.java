package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.OrderDishDto;
import fu.rms.entity.OrderDish;
import fu.rms.interfacedto.OrderDishOptionDto;
import fu.rms.interfacedto.OrderDishOptionMapper;
import fu.rms.mapper.OrderDishMapper;
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

	@Override
	public List<OrderDishDto> getListOrderDishByOrder(Long orderId) {

		List<OrderDish> listOrderDish = orderDishRepo.getOrderDishByOrder(orderId);
		List<OrderDishDto> listDto = listOrderDish.stream().map(orderDishMapper::entityToDto)
				.collect(Collectors.toList());	
		
		for (int i = 0; i < listOrderDish.size(); i++) {
			List<OrderDishOptionDto> listOrderDishOption = new ArrayList<OrderDishOptionDto>();
			if(listDto.get(i).getOrderDishOptions() != null) {
				
				listOrderDishOption = listOrderDish.get(i).getOrderDishOptions()
				.stream().map(orderDishOptionMapper::entityToDto).collect(Collectors.toList());	;
			}
			listDto.get(i).setOrderDishOptions(listOrderDishOption);
		}
		
		return listDto;
	}

	@Override
	public int insertOrderDish(OrderDishDto dto) {

		if(dto != null) {
			
		}
		return 1;

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
