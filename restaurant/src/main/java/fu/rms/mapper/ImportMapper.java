package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.ImportDto;
import fu.rms.entity.Import;

@Component
public class ImportMapper {
	
	@Autowired
	ModelMapper modelMapper;
	
	public ImportDto entityToDto(Import entity) {		
		ImportDto dto = modelMapper.map(entity, ImportDto.class);
		return dto;
	}
	
	public Import dtoToEntity(ImportDto dto) {
		Import entity = modelMapper.map(dto, Import.class);
		return entity;
	}
}
