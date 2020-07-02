package fu.rms.mapper;

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
		if(dto.getOrderId() != null) {
			String orderTime = Utils.getOrderTime(Utils.getCurrentTime(), dto.getOrderOrderDate());
			dto.setOrderTime(orderTime);
		}

		return dto;
	}

	public Tables dtoToEntity(TableDto tableDto) {
		Tables table = modelMapper.map(tableDto, Tables.class);
		return table;
	}
}
