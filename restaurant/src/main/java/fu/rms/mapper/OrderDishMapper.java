package fu.rms.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.OrderDishDto;
import fu.rms.entity.Dish;
import fu.rms.entity.Order;
import fu.rms.entity.OrderDish;
import fu.rms.entity.OrderDishCancel;
import fu.rms.entity.OrderDishOption;
import fu.rms.entity.Status;
import fu.rms.newDto.OrderDishOptionChef;
import fu.rms.newDto.mapper.OrderDishChef;
import fu.rms.newDto.mapper.OrderDishOptionMapper;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.utils.Utils;

@Component
public class OrderDishMapper {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	StatusRepository statusRepo;
	
	@Autowired
	OptionRepository optionRepo;
	
	@Autowired
	OrderDishOptionMapper odoMapper;
	
	public OrderDishDto entityToDto(OrderDish entity) {
		OrderDishDto dto = modelMapper.map(entity, OrderDishDto.class);
		return dto;
	}
	
	public OrderDish dtoToEntity(OrderDishDto dto) {
		
		OrderDish entity = new OrderDish();
		entity.setComment(dto.getComment());
		entity.setSellPrice(dto.getSellPrice());
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
		
		return entity;
	}
	
	public OrderDishChef entityToChef(OrderDish entity) {
	
		OrderDishChef orderDishChef = new OrderDishChef();
		orderDishChef.setOrderDishId(entity.getOrderDishId());
		orderDishChef.setStatusId(entity.getStatus().getStatusId());
		orderDishChef.setDishId(entity.getDish().getDishId());
		orderDishChef.setDishName(entity.getDish().getDishName());
		orderDishChef.setQuantityOk(entity.getQuantityOk());
		orderDishChef.setComment(entity.getComment());
		if(entity.getCreateDate()!=null) {
			orderDishChef.setCreatedDate(Utils.getOrderTime(Utils.getCurrentTime(), entity.getCreateDate()));
		}
		orderDishChef.setTimeToComplete(entity.getDish().getTimeComplete());
		orderDishChef.setTimeToNotification(entity.getDish().getTimeNotification());
		orderDishChef.setStatusValue(entity.getStatus().getStatusValue());
		orderDishChef.setCheckNotification(Utils.getTimeToNotification(entity.getCreateDate(), orderDishChef.getTimeToNotification()));
		
		List<OrderDishOptionChef> listDishOptions = new ArrayList<OrderDishOptionChef>();
		if(entity.getOrderDishOptions() != null) {
			listDishOptions = entity.getOrderDishOptions().stream().map(odoMapper::entityToChef).collect(Collectors.toList());
		}
		orderDishChef.setOrderDishOptions(listDishOptions);
		
		return orderDishChef;
	}
}
