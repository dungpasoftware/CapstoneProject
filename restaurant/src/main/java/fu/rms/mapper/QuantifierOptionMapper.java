package fu.rms.mapper;

import org.springframework.stereotype.Component;

import fu.rms.dto.QuantifierOptionDto;
import fu.rms.dto.QuantifierOptionDto.MaterialQuantifierOption;
import fu.rms.entity.QuantifierOption;

@Component
public class QuantifierOptionMapper {

	public QuantifierOptionDto entityToDto(QuantifierOption quantifierOption) {
		QuantifierOptionDto quantifierOptionDto=new QuantifierOptionDto();
		quantifierOptionDto.setQuantifierOptionId(quantifierOption.getQuantifierOptionId());
		quantifierOptionDto.setQuantity(quantifierOption.getQuantity());
		quantifierOptionDto.setUnit(quantifierOption.getUnit());
		quantifierOptionDto.setCost(quantifierOption.getCost());
		quantifierOptionDto.setDescription(quantifierOption.getDescription());
		if(quantifierOption.getMaterial()!=null) {
			MaterialQuantifierOption materialQuantifierOption=new MaterialQuantifierOption();
			materialQuantifierOption.setMaterialId(quantifierOption.getMaterial().getMaterialId());
			materialQuantifierOption.setMaterialName(quantifierOption.getMaterial().getMaterialName());
			materialQuantifierOption.setUnit(quantifierOption.getMaterial().getUnit());
			materialQuantifierOption.setUnitPrice(quantifierOption.getMaterial().getUnitPrice());
			quantifierOptionDto.setMaterial(materialQuantifierOption);
		}
		return quantifierOptionDto;
	}
	
	public QuantifierOption dtoToEntity(QuantifierOptionDto quantifierOptionDto) {
		QuantifierOption quantifierOption=new QuantifierOption();
		quantifierOption.setQuantifierOptionId(quantifierOptionDto.getQuantifierOptionId());
		quantifierOption.setQuantity(quantifierOptionDto.getQuantity());
		quantifierOption.setUnit(quantifierOptionDto.getUnit());
		quantifierOption.setCost(quantifierOptionDto.getCost());
		quantifierOption.setDescription(quantifierOptionDto.getDescription());
		return quantifierOption;
	}
}
