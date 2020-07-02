package fu.rms.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.LocationTableDto;
import fu.rms.dto.TableDto;
import fu.rms.entity.LocationTable;
import fu.rms.entity.Tables;

@Component
public class LocationTableMapper {

	@Autowired
	private TableMapper tableMapper;
	
	@Autowired
	ModelMapper modelMapper;

	public LocationTableDto entityToDto(LocationTable locationTable) {
		
//		List<Tables> tables = locationTable.getTables();
//		List<TableDto> tableDtos = tables.stream().map(tableMapper::entityToDto).collect(Collectors.toList());
//
//		PropertyMap<LocationTable, LocationTableDto> propertyMap = new PropertyMap<LocationTable, LocationTableDto>() {
//			@Override
//			protected void configure() {
////				map().setStatusId(source.getStatus().getStatusId());
////				map().setStatusValue(source.getStatus().getStatusValue());
//				map().setTables(tableDtos);
//
//			}
//		};
//		modelMapper.addMappings(propertyMap);
		return modelMapper.map(locationTable, LocationTableDto.class);

	}

	public LocationTable dtoToEntity(LocationTableDto locationTableDto) {
		return modelMapper.map(locationTableDto, LocationTable.class);
	}
}
