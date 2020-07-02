package fu.rms.dto;

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
	
	private Long staffId;
	
	private String staffCode;
}
