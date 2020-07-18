package fu.rms.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.ImportMaterialDto;
import fu.rms.dto.MaterialDto;
import fu.rms.entity.ImportMaterial;

@Component
public class ImportMaterialMapper {

	@Autowired
	private MaterialMapper materialMapper;
	
	public ImportMaterialDto entityToDto(ImportMaterial entity) {		
		ImportMaterialDto dto = new ImportMaterialDto();
		dto.setImportMaterialId(entity.getImportMaterialId());
		dto.setExpireDate(entity.getExpireDate());
		dto.setQuantityImport(entity.getQuantityImport());
		dto.setQuantityExport(entity.getQuantityExport());
		dto.setPrice(entity.getPrice());
		dto.setSumPrice(entity.getSumPrice());
		dto.setExpireDate(entity.getExpireDate());
		if(entity.getImports() != null) {
			dto.setImportId(entity.getImports().getImportId());
		}
		if(entity.getMaterial() != null) {
			MaterialDto materialDto = materialMapper.entityToDto(entity.getMaterial());
			dto.setMaterial(materialDto);
		}	
		return dto;
	}
	
	public ImportMaterial dtoToEntity(ImportMaterialDto dto) {
		ImportMaterial entity = new ImportMaterial();
		entity.setImportMaterialId(dto.getImportMaterialId());
		entity.setExpireDate(dto.getExpireDate());
		entity.setQuantityImport(dto.getQuantityImport());
		entity.setQuantityExport(dto.getQuantityExport());
		entity.setPrice(dto.getPrice());
		entity.setSumPrice(dto.getSumPrice());
		entity.setExpireDate(dto.getExpireDate());
		
		return entity;
	}
}
