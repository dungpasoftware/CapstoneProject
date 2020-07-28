package fu.rms.newDto;

import java.util.List;

import fu.rms.newDto.mapper.OrderDishChef;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderChef {

	private Long orderId;
	
	private Long tableId;
	
	private String tableName;
	
	private Long statusId;
	
	private String statusValue;
	
	private String timeOrder;	// thơi gian đã order đc bao lâu
	
	private String comment;
	
	private List<OrderDishChef> orderDish;
	
	
}
