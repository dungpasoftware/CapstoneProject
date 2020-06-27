package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.TableDto;
import fu.rms.entity.Tables;

@Component
public class TableMapper {

	@Autowired
	ModelMapper modelMapper;
	
	
	public TableDto entityToDto(Tables table) {
		TableDto tableDto=modelMapper.map(table,TableDto.class);
		return tableDto;
	}
	
	public Tables dtoToEntity(TableDto tableDto) {
		Tables table=modelMapper.map(tableDto,Tables.class);
		return table;
	}
}
