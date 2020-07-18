package fu.rms.newDto;

import java.util.List;

import fu.rms.dto.OrderDishDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetail { // for view detail 
	
	private Long orderId;
	
	private String orderCode;
	
	private Long statusId;
	
	private Double totalAmount;
	
	private Integer totalItem;
	
	private String comment;
	
	private Long tableId;
	
	private String tableName;
	
	private List<OrderDishDto> orderDish;
	
	
}
