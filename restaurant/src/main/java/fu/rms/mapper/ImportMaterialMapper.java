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
	
	public ImportMaterialDto entityToDto(ImportMaterial importMaterial) {		
		ImportMaterialDto importMaterialDto = new ImportMaterialDto();
		importMaterialDto.setImportMaterialId(importMaterial.getImportMaterialId());
		importMaterialDto.setExpireDate(importMaterial.getExpireDate());
		importMaterialDto.setQuantityImport(importMaterial.getQuantityImport());
		importMaterialDto.setQuantityExport(importMaterial.getQuantityExport());
		importMaterialDto.setPrice(importMaterial.getPrice());
		importMaterialDto.setSumPrice(importMaterial.getSumPrice());
		importMaterialDto.setExpireDate(importMaterial.getExpireDate());
		if(importMaterial.getImports() != null) {
			importMaterialDto.setImportId(importMaterial.getImports().getImportId());
		}
		if(importMaterial.getMaterial() != null) {
			MaterialDto materialDto = materialMapper.entityToDto(importMaterial.getMaterial());
			importMaterialDto.setMaterial(materialDto);
		}	
		return importMaterialDto;
	}
	
	public ImportMaterial dtoToEntity(ImportMaterialDto importMaterialDto) {
		ImportMaterial importMaterial = new ImportMaterial();
		importMaterial.setImportMaterialId(importMaterialDto.getImportMaterialId());
		importMaterial.setExpireDate(importMaterialDto.getExpireDate());
		importMaterial.setQuantityImport(importMaterialDto.getQuantityImport());
		importMaterial.setQuantityExport(importMaterialDto.getQuantityExport());
		importMaterial.setPrice(importMaterialDto.getPrice());
		importMaterial.setSumPrice(importMaterialDto.getSumPrice());
		importMaterial.setExpireDate(importMaterialDto.getExpireDate());
		
		return importMaterial;
	}
}
