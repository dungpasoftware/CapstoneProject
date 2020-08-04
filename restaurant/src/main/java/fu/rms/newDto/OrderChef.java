package fu.rms.newDto;

import java.sql.Timestamp;
import java.util.List;

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
	
	private Integer totalQuantity;
	
	private Timestamp createdDate;
	
	private String timeOrder;	// thơi gian đã order đc bao lâu
	
	private String comment;
	
	private List<OrderDishChef> orderDish;
	
	
}
