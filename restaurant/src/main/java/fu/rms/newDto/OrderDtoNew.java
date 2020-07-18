package fu.rms.newDto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoNew {	//for table
	
	private Long orderId;
	
	private String orderCode;
	
	private Long statusId;
	
	private String orderStatusValue;
	
	private String comment;
	
	private Double totalAmount;
	
	private Integer totalItem;
	
	private Timestamp orderDate;
	
	private String orderTime;
}
