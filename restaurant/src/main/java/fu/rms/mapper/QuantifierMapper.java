package fu.rms.mapper;

import org.springframework.stereotype.Component;

import fu.rms.dto.QuantifierDto;
import fu.rms.dto.QuantifierDto.MaterialQuantifier;
import fu.rms.entity.Quantifier;

@Component
public class QuantifierMapper {

	public QuantifierDto entityToDto(Quantifier quantifier) {
		QuantifierDto quantifierDto=new QuantifierDto();
		quantifierDto.setQuantifierId(quantifier.getQuantifierId());
		quantifierDto.setQuantity(quantifier.getQuantity());
		quantifierDto.setUnit(quantifier.getUnit());
		quantifierDto.setCost(quantifier.getCost());
		quantifierDto.setDescription(quantifier.getDescription());
		if(quantifier.getMaterial()!=null) {
			MaterialQuantifier materialQuantifier=new MaterialQuantifier();
			materialQuantifier.setMaterialId(quantifier.getMaterial().getMaterialId());
			materialQuantifier.setMaterialName(quantifier.getMaterial().getMaterialName());
			materialQuantifier.setUnit(quantifier.getMaterial().getUnit());
			materialQuantifier.setUnitPrice(quantifier.getMaterial().getUnitPrice());
			quantifierDto.setMaterial(materialQuantifier);
		}
		return quantifierDto;
	}
	
}
