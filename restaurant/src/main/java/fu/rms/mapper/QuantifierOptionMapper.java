package fu.rms.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.MaterialDto;
import fu.rms.dto.QuantifierOptionDto;
import fu.rms.entity.QuantifierOption;

@Component
public class QuantifierOptionMapper {
	
	@Autowired
	private MaterialMapper materialMapper;
	public QuantifierOptionDto entityToDto(QuantifierOption quantifierOption) {
		QuantifierOptionDto quantifierOptionDto=new QuantifierOptionDto();
		quantifierOptionDto.setQuantifierOptionId(quantifierOption.getQuantifierOptionId());
		quantifierOptionDto.setQuantity(quantifierOption.getQuantity());
		quantifierOptionDto.setUnit(quantifierOption.getUnit());
		quantifierOptionDto.setCost(quantifierOption.getCost());
		quantifierOptionDto.setDescription(quantifierOption.getDescription());
		if(quantifierOption.getMaterial()!=null) {
			MaterialDto materialDto=materialMapper.entityToDto(quantifierOption.getMaterial());
			quantifierOptionDto.setMaterial(materialDto);
		}
		return quantifierOptionDto;
	}
}
