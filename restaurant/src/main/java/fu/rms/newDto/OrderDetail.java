package fu.rms.newDto;

import java.util.ArrayList;
import java.util.List;

import fu.rms.dto.OrderDishDto;
import fu.rms.entity.Dish;
import fu.rms.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail { // for view detail 
	
	private Long orderId;
	
	private String orderCode;
	
	private Long statusId;
	
	private Double totalAmount;
	
	private Integer totalItem;
	
	private String comment;
	
	private Long tableId;
	
	private String tableName;
	
	private List<OrderDishDto> orderDish = new ArrayList<OrderDishDto>();
	
	
}
