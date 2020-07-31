package fu.rms.newDto.mapper;

import java.util.List;

import fu.rms.newDto.OrderDishOptionChef;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDishChef {

	private Long orderDishId;
	
	private Long dishId;
	
	private String dishName;
	
	private Long statusId;
	
	private String statusValue;
	
	private String comment;
	
	private Integer quantityOk;
	
	private String createdDate;
	
	private Float timeRemain;
	
	private Float timeToComplete;
	
	private Float timeToNotification;
	
	private List<OrderDishOptionChef> orderDishOptions;
	
}
