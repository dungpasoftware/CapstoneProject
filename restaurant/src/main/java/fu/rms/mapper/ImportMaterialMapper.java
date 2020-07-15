package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.ImportMaterialDto;
import fu.rms.entity.ImportMaterial;

@Component
public class ImportMaterialMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public ImportMaterialDto entityToDto(ImportMaterial entity) {		
		ImportMaterialDto dto = modelMapper.map(entity, ImportMaterialDto.class);
		return dto;
	}
	
	public ImportMaterial dtoToEntity(ImportMaterialDto dto) {
		ImportMaterial entity = modelMapper.map(dto, ImportMaterial.class);
		return entity;
	}
}
