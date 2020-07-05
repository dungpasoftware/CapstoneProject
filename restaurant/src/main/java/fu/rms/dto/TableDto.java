package fu.rms.dto;

import fu.rms.newDto.OrderDtoNew;
import fu.rms.newDto.StaffDtoNew;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableDto {

	private Long tableId;
	
	private String tableCode;

	private String tableName;

	private Integer minCapacity;

	private Integer maxCapacity;
	
	private String statusValue;
	
	private OrderDtoNew order;	
	
	private StaffDtoNew staff;
}
