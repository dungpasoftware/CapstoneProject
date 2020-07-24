package fu.rms.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.OrderDishDto;
import fu.rms.entity.Dish;
import fu.rms.entity.Option;
import fu.rms.entity.Order;
import fu.rms.entity.OrderDish;
import fu.rms.entity.OrderDishCancel;
import fu.rms.entity.OrderDishOption;
import fu.rms.entity.Status;
import fu.rms.newDto.OrderDishOptionDtoNew;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.StatusRepository;

@Component
public class OrderDishMapper {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	StatusRepository statusRepo;
	
	@Autowired
	OptionRepository optionRepo;
	
	public OrderDishDto entityToDto(OrderDish entity) {
//		
//		OrderDishDto dto = new OrderDishDto();
//		dto.setOrderDishId(entity.getOrderDishId());
//		dto.setQuantity(entity.getQuantity());
//		dto.setSellPrice(entity.getSellPrice());
//		dto.setStatusOrderDishId(entity.getStatus().getStatusId());
//		dto.setStatusOrderDishValue(entity.getStatus().getStatusValue());
//		dto.setOrderId(orderId);
//		
		OrderDishDto dto = modelMapper.map(entity, OrderDishDto.class);
		return dto;
	}
	
	public OrderDish dtoToEntity(OrderDishDto dto) {
		
		OrderDish entity = new OrderDish();
//		entity.setOrderDishId(dto.getOrderDishId());
//		entity.setQuantity(dto.getQuantity());
//		entity.setQuantityCancel(dto.getQuantityCancel());
//		entity.setQuantityOk(dto.getQuantityOk());
		entity.setComment(dto.getComment());
//		entity.setModifiedBy(dto.getModifiedBy());
//		entity.setModifiedDate(dto.getModifiedDate());
//		entity.setCreateBy(dto.getCreateBy());
//		entity.setCreateDate(dto.getCreateDate());
		entity.setSellPrice(dto.getSellPrice());
//		entity.setSumPrice(dto.getSumPrice());
		Status status = statusRepo.findById(StatusConstant.STATUS_ORDER_DISH_ORDERED).get();
		entity.setStatus(status);
		Order order = new Order();
		order.setOrderId(dto.getOrderOrderId());
		entity.setOrder(order);
		Dish dish = new Dish();
		dish.setDishId(dto.getDish().getDishId());
		dish.setDishName(dto.getDish().getDishName());
		dish.setDishUnit(dto.getDish().getDishUnit());
		dish.setDefaultPrice(dto.getDish().getDefaultPrice());
		entity.setDish(dish);
		List<OrderDishOption> listOdo = new ArrayList<OrderDishOption>();
		List<OrderDishCancel> listCancel = new ArrayList<OrderDishCancel>();
		entity.setOrderDishOptions(listOdo);
		entity.setOrderDishCancels(listCancel);
		
//		OrderDish entity = modelMapper.map(dto, OrderDish.class);
		return entity;
	}
}
