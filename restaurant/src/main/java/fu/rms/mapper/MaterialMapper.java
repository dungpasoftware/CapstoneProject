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
	
	public MaterialDto entityToDto(Material material) {		
		MaterialDto materialDto = new MaterialDto();
		materialDto.setMaterialId(material.getMaterialId());
		materialDto.setMaterialName(material.getMaterialName());
		materialDto.setMaterialCode(material.getMaterialCode());
		materialDto.setUnit(material.getUnit());
		materialDto.setUnitPrice(material.getUnitPrice());
		materialDto.setTotalPrice(material.getTotalPrice());
		materialDto.setTotalImport(material.getTotalImport());
		materialDto.setTotalExport(material.getTotalExport());
		materialDto.setRemain(material.getRemain());
		materialDto.setRemainNotification(material.getRemainNotification());
		if(material.getStatus() != null) {
			materialDto.setStatusId(material.getStatus().getStatusId());
			materialDto.setStatusValue(material.getStatus().getStatusValue());
		}
		if(material.getGroupMaterial() != null) {
			GroupMaterialDto groupDto = new GroupMaterialDto();
			groupDto.setGroupId(material.getGroupMaterial().getGroupId());
			groupDto.setGroupName(material.getGroupMaterial().getGroupName());
			materialDto.setGroupMaterial(groupDto);
		}
		return materialDto;
	}
	
	public Material dtoToEntity(MaterialDto materialDto) {
		Material material = new Material();
		material.setMaterialId(materialDto.getMaterialId());
		material.setMaterialName(materialDto.getMaterialName());
		material.setMaterialCode(materialDto.getMaterialCode());
		material.setUnit(materialDto.getUnit());
		material.setUnitPrice(materialDto.getUnitPrice());
		material.setTotalPrice(materialDto.getTotalPrice());
		material.setTotalImport(materialDto.getTotalImport());
		material.setTotalExport(materialDto.getTotalExport());
		material.setRemain(materialDto.getRemain());
		material.setRemainNotification(materialDto.getRemainNotification());
		return material;
	}
	
}
