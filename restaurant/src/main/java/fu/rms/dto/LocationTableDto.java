package fu.rms.dto;

import java.util.List;

import fu.rms.entity.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationTableDto {

	private Long locationTableId;
	
	private String locationCode;
	
	private String locationName;
	
//	private String statusValue;
//	
//	private List<TableDto> tables;
//	
//	@Getter
//	@Setter
//	public static class TableDto{
//		private long tableId;
//		private String tableName;
//		private String statusValue;
//		
//	}
	
}
