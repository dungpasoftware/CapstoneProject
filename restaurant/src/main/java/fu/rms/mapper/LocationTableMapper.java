package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.LocationTableDto;
import fu.rms.entity.LocationTable;

@Component
public class LocationTableMapper {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	StatusMapper statusMapper;

	public LocationTableDto entityToDto(LocationTable entity) {
		LocationTableDto dto = new LocationTableDto();
//		dto.setLocationTableId(entity.getLocationTableId());
//		dto.setLocationName(entity.getLocationName());
//		dto.setLocationCode(entity.getLocationCode());
//		dto.setStatus(statusMapper.entityToDto(entity.getStatus()));
		dto = modelMapper.map(entity, LocationTableDto.class);
		return dto;

	}

	public LocationTable dtoToEntity(LocationTableDto dto) {
		return modelMapper.map(dto, LocationTable.class);
	}

}
