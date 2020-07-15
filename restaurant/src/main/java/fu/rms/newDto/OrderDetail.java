package fu.rms.newDto;

import java.sql.Timestamp;
import java.util.List;

import fu.rms.dto.OrderDishDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetail { // for view detail 
	
	private Long orderId;
	
	private String orderCode;
	
	private Long orderStatusId;
	
	private Double totalAmount;
	
	private Integer totalItem;
	
	private String comment;
	
	private Long tableId;
	
	private List<OrderDishDto> orderDish;
	
	
}
