package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.constant.Utils;
import fu.rms.dto.TableDto;
import fu.rms.entity.Tables;

@Component
public class TablesMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public TableDto entityToDto(Tables table) {

		TableDto dto = modelMapper.map(table, TableDto.class);
		return dto;
		
	}

	public Tables dtoToEntity(TableDto tableDto) {
		Tables table = modelMapper.map(tableDto, Tables.class);
		return table;
	}
	
	public TableDto toDto(String tableCode, String tableName, Integer maxCapacity, String statusValue) {
		TableDto dto = new TableDto();
		dto.setMaxCapacity(maxCapacity);
//		dto.setMinCapacity(minCapacity);
		dto.setTableCode(tableCode);
//		dto.setTableId(tableId);
		dto.setTableName(tableName);
		dto.setStatusValue(statusValue);
		return dto;
	}
	
}
