package fu.rms.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.ImportDto;
import fu.rms.dto.ImportMaterialDto;
import fu.rms.dto.SupplierDto;
import fu.rms.dto.WarehouseDto;
import fu.rms.entity.Import;

@Component
public class ImportMapper {
	
	
	@Autowired
	private ImportMaterialMapper importMaterialMapper;		
	
	@Autowired
	private WarehouseMapper warehouseMapper;
	
	@Autowired
	private SupplierMapper supplierMapper;
	
	
	
	public ImportDto entityToDto(Import importEntity) {		
		ImportDto importDto = new ImportDto();
		importDto.setImportId(importEntity.getImportId());
		importDto.setImportCode(importEntity.getImportCode());
		importDto.setTotalAmount(importEntity.getTotalAmount());
		importDto.setComment(importEntity.getComment());
		importDto.setCreatedBy(importEntity.getCreatedBy());
		importDto.setCreationDate(importEntity.getCreationDate());
		importDto.setLastModifiedBy(importEntity.getLastModifiedBy());
		importDto.setLastModifiedDate(importEntity.getLastModifiedDate());
		if(importEntity.getImportMaterials() != null && !importEntity.getImportMaterials().isEmpty()) {
			List<ImportMaterialDto> listImportMaterialDto = importEntity.getImportMaterials()
					.stream().map(importMaterialMapper::entityToDto).collect(Collectors.toList());
			importDto.setImportMaterials(listImportMaterialDto);
		}
		if(importEntity.getWarehouse()!=null) {
			WarehouseDto warehouseDto=warehouseMapper.entityToDto(importEntity.getWarehouse());
			importDto.setWarehouse(warehouseDto);		
		}
		if(importEntity.getSupplier()!=null) {
			SupplierDto supplierDto=supplierMapper.entityToDto(importEntity.getSupplier());
			importDto.setSupplier(supplierDto);
		}
		
		return importDto;
	}
	
	public Import dtoToEntity(ImportDto importDto) {
		Import importEntity = new Import();
		importEntity.setImportId(importDto.getImportId());
		importEntity.setImportCode(importDto.getImportCode());
		importEntity.setTotalAmount(importDto.getTotalAmount());
		importEntity.setComment(importDto.getComment());		
		importDto.setCreatedBy(importEntity.getCreatedBy());
		importDto.setCreationDate(importEntity.getCreationDate());
		importDto.setLastModifiedBy(importEntity.getLastModifiedBy());
		importDto.setLastModifiedDate(importEntity.getLastModifiedDate());
		
		return importEntity;
	}
}
