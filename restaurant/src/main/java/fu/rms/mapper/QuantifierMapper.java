package fu.rms.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.MaterialDto;
import fu.rms.dto.QuantifierDto;
import fu.rms.entity.Quantifier;

@Component
public class QuantifierMapper {
	
	@Autowired
	private MaterialMapper materialMapper;

	public QuantifierDto entityToDto(Quantifier quantifier) {
		QuantifierDto quantifierDto=new QuantifierDto();
		quantifierDto.setQuantifierId(quantifier.getQuantifierId());
		quantifierDto.setQuantity(quantifier.getQuantity());
		quantifierDto.setUnit(quantifier.getUnit());
		quantifierDto.setCost(quantifier.getCost());
		quantifierDto.setDescription(quantifier.getDescription());
		if(quantifier.getMaterial()!=null) {
			MaterialDto materialDto=materialMapper.entityToDto(quantifier.getMaterial());
			quantifierDto.setMaterial(materialDto);
		}
		return quantifierDto;
	}
	
}
