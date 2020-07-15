package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.MaterialDto;
import fu.rms.entity.Material;

@Component
public class MaterialMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public MaterialDto entityToDto(Material entity) {		
		MaterialDto dto = modelMapper.map(entity, MaterialDto.class);
		return dto;
	}
	
	public Material dtoToEntity(MaterialDto dto) {
		Material entity = modelMapper.map(dto, Material.class);
		return entity;
	}
	
}
