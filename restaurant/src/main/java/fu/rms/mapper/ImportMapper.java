package fu.rms.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.ImportDto;
import fu.rms.dto.ImportMaterialDto;
import fu.rms.entity.Import;

@Component
public class ImportMapper {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ImportMaterialMapper importMaterialMapper;
	
	
	public ImportDto entityToDto(Import entity) {		
		ImportDto dto = new ImportDto();
		dto.setImportId(entity.getImportId());
		dto.setImportCode(entity.getImportCode());
		dto.setImportDate(entity.getImportDate());
		dto.setImportBy(entity.getImportBy());
		dto.setTotalAmount(entity.getTotalAmount());
		dto.setComment(entity.getComment());
		
		if(entity.getImportMaterials() != null && !entity.getImportMaterials().isEmpty()) {
			List<ImportMaterialDto> listImportMaterialDto = entity.getImportMaterials()
					.stream().map(importMaterialMapper::entityToDto).collect(Collectors.toList());
			dto.setImportMaterials(listImportMaterialDto);
		}
		
		return dto;
	}
	
	public Import dtoToEntity(ImportDto dto) {
		Import entity = new Import();
		entity.setImportId(dto.getImportId());
		entity.setImportCode(dto.getImportCode());
		entity.setImportDate(dto.getImportDate());
		entity.setImportBy(dto.getImportBy());
		entity.setTotalAmount(dto.getTotalAmount());
		entity.setComment(dto.getComment());		
		return entity;
	}
}
