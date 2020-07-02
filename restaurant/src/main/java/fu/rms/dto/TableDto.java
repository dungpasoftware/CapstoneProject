package fu.rms.dto;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableDto {

	private Long tableId;
	
	private String tableCode;

	private String tableName;
	
	private Long locationTableId;

	private String locationName;

	private Integer minCapacity;

	private Integer maxCapacity;
	
	private Long statusId;

	private String statusValue;

	private Long orderId;
	
	private String orderCode;
	
	private Long orderStatusId;
	
	private String orderStatusValue;
	
	private Timestamp orderOrderDate;
	
	private String orderTime;
	
	private Long staffId;
	
	private String staffCode;
}
