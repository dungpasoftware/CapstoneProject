package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.GroupMaterialDto;
import fu.rms.dto.MaterialDto;
import fu.rms.entity.Material;

@Component
public class MaterialMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public MaterialDto entityToDto(Material entity) {		
		MaterialDto dto = new MaterialDto();
		dto.setMaterialId(entity.getMaterialId());
		dto.setMaterialName(entity.getMaterialName());
		dto.setMaterialCode(entity.getMaterialCode());
		dto.setUnitImport(entity.getUnitImport());
		dto.setUnitExport(entity.getUnitExport());
		dto.setRating(entity.getRating());
		dto.setUnitImportPrice(entity.getUnitImportPrice());
		dto.setUnitExportPrice(entity.getUnitExportPrice());
		dto.setTotalImport(entity.getTotalImport());
		dto.setTotalExport(entity.getTotalExport());
		dto.setRemain(entity.getRemain());
		dto.setRemainUnitImport(entity.getRemainUnitImport());
		dto.setRemainNotifycation(entity.getRemainNotifycation());
		if(entity.getStatus() != null) {
			dto.setStatusId(entity.getStatus().getStatusId());
			dto.setStatusValue(entity.getStatus().getStatusValue());
		}
		if(entity.getGroupMaterial() != null) {
			GroupMaterialDto groupDto = new GroupMaterialDto();
			groupDto.setGroupId(entity.getGroupMaterial().getGroupId());
			groupDto.setGroupName(entity.getGroupMaterial().getGroupName());
			dto.setGroupMaterial(groupDto);
		}
		return dto;
	}
	
	public Material dtoToEntity(MaterialDto dto) {
		Material entity = new Material();
		entity.setMaterialId(dto.getMaterialId());
		entity.setMaterialName(dto.getMaterialName());
		entity.setMaterialCode(dto.getMaterialCode());
		entity.setUnitImport(dto.getUnitImport());
		entity.setUnitExport(dto.getUnitExport());
		entity.setRating(dto.getRating());
		entity.setUnitImportPrice(dto.getUnitImportPrice());
		entity.setUnitExportPrice(dto.getUnitExportPrice());
		entity.setTotalImport(dto.getTotalImport());
		entity.setTotalExport(dto.getTotalExport());
		entity.setRemain(dto.getRemain());
		entity.setRemainUnitImport(dto.getRemainUnitImport());
		entity.setRemainNotifycation(dto.getRemainNotifycation());
		return entity;
	}
	
}
