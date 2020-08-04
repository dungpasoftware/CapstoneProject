package fu.rms.mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.OrderDto;
import fu.rms.entity.Order;
import fu.rms.newDto.OrderChef;
import fu.rms.newDto.OrderDetail;
import fu.rms.newDto.OrderDishChef;

@Component
public class OrderMapper {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	OrderDishMapper orderDishMapper;
	
	public OrderDto entityToDto(Order entity) {
		
		OrderDto dto = modelMapper.map(entity, OrderDto.class);
		return dto;
	}
	
	public Order dtoToEntity(OrderDto dto) {
		Order entity = modelMapper.map(dto, Order.class);
		return entity;
	}
	
	public OrderDetail entityToDetail(Order entity) {
		OrderDetail detail = new OrderDetail();
		if(entity != null) {
			detail = modelMapper.map(entity, OrderDetail.class);
		}
		return detail;
	}
	
	public OrderDetail dtoToDetail(OrderDto dto) {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setComment(dto.getComment());
		orderDetail.setOrderCode(dto.getOrderCode());
		orderDetail.setOrderId(dto.getOrderId());
		orderDetail.setTotalItem(dto.getTotalItem());
		orderDetail.setStatusId(dto.getStatusId());
		orderDetail.setTotalAmount(dto.getTotalAmount());
		return orderDetail;
	}
	
	public OrderChef entityToChef(Order entity) {
		
		OrderChef orderChef = new OrderChef();
		orderChef.setOrderId(entity.getOrderId());
		orderChef.setTableName(entity.getTable().getTableName());
		orderChef.setTableId(entity.getTable().getTableId());
		orderChef.setStatusId(entity.getStatus().getStatusId());
		orderChef.setStatusValue(entity.getStatus().getStatusValue());
		orderChef.setComment(entity.getComment());
//		if(entity.getOrderDate() != null) {
//			orderChef.setTimeOrder(Utils.getOrderTime(Utils.getCurrentTime(), entity.getOrderDate()));
//		}
		
		List<OrderDishChef> listDishChef = new ArrayList<OrderDishChef>();
		if(entity.getOrderDish().size() != 0) {
			listDishChef = entity.getOrderDish().stream().map(orderDishMapper::entityToChef).collect(Collectors.toList());
		}
		listDishChef = listDishChef.stream()													// nếu là đang (ordered hoặc preparation) và quantity khác 0 thì giữ lại
				.filter(dishChef -> ((dishChef.getStatusId().equals(StatusConstant.STATUS_ORDER_DISH_ORDERED) 
						|| dishChef.getStatusId().equals(StatusConstant.STATUS_ORDER_DISH_PREPARATION)))
						&& !dishChef.getQuantityOk().equals(0))
				.collect(Collectors.toList());

		int sumQuantity = 0;
		if(listDishChef.size() != 0) {
			for (OrderDishChef orderDishChef : listDishChef) {									// nếu ko xóa thì cộng vào totalquantity;
				Integer quantityOk = orderDishChef.getQuantityOk();
				sumQuantity += quantityOk;
			}
			listDishChef.stream().sorted(Comparator.comparing(OrderDishChef::getDate)).collect(Collectors.toList());	// so sánh xem thằng nào time muộn nhất
			orderChef.setTimeOrder(listDishChef.get(0).getCreatedDate());												// lấy time theo thằng time muộn nhất
			orderChef.setCreatedDate(listDishChef.get(0).getDate());													
		}

		orderChef.setTotalQuantity(sumQuantity);
		orderChef.setOrderDish(listDishChef);
		return orderChef;
	}
	
}

