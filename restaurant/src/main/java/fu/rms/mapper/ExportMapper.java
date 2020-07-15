package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.ExportDto;
import fu.rms.entity.Export;

@Component
public class ExportMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public ExportDto entityToDto(Export entity) {		
		ExportDto dto = modelMapper.map(entity, ExportDto.class);
		return dto;
	}
	
	public Export dtoToEntity(ExportDto dto) {
		Export entity = modelMapper.map(dto, Export.class);
		return entity;
	}
}
