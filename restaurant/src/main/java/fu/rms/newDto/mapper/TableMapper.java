package fu.rms.newDto.mapper;

import org.springframework.stereotype.Component;
import fu.rms.dto.TableDto;

@Component
public class TableMapper {
	
	public TableDto toDto(String tableCode, String tableName, Integer maxCapacity, String tableStatus) {
		TableDto dto = new TableDto();
		dto.setMaxCapacity(maxCapacity);
		dto.setTableCode(tableCode);
		dto.setTableName(tableName);
		dto.setStatusValue(tableStatus);
		return dto;
	}
	
}
