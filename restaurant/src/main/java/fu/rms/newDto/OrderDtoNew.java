package fu.rms.newDto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDtoNew {
	
	private Long orderId;
	
	private String orderCode;
	
	private Long orderStatusId;
	
	private String orderStatusValue;
	
	private Timestamp orderDate;
	
	private String orderTime;
}
