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
	
	private Long orderStatusId;
	
	private String orderStatusValue;
	
	private Timestamp orderDate;
	
	private String orderTime;
}
