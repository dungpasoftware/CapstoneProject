package fu.rms.interfacedto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.constant.Utils;
import fu.rms.dto.TableDto;
import fu.rms.entity.Tables;

@Component
public class TableMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public TableDto entityToDto(Tables table) {

		TableDto dto = modelMapper.map(table, TableDto.class);
//		if(dto.getOrderDto() != null) {
//			String orderTime = Utils.getOrderTime(Utils.getCurrentTime(), dto.getOrderDto().getOrderDate());
//			dto.setOrderTime(orderTime);
//		}

		return dto;
	}

	public Tables dtoToEntity(TableDto tableDto) {
		Tables table = modelMapper.map(tableDto, Tables.class);
		return table;
	}
	
	public TableDto toDto(String tableCode, String tableName, Integer maxCapacity, String tableStatus) {
		TableDto dto = new TableDto();
		dto.setMaxCapacity(maxCapacity);
//		dto.setMinCapacity(minCapacity);
		dto.setTableCode(tableCode);
//		dto.setTableId(tableId);
		dto.setTableName(tableName);
		dto.setTableStatus(tableStatus);
		return dto;
	}
	
}
