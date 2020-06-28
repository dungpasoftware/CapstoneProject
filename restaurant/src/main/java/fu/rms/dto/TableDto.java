package fu.rms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableDto {

	private Long tableId;
	
	private String tableCode;

	private String tableName;

	private String locationName;

	private int minCapacity;

	private int maxCapacity;

	private String statusValue;

}
