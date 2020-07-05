package fu.rms.mapper;

import org.springframework.stereotype.Component;

import fu.rms.dto.OptionDto;
import fu.rms.entity.Option;
@Component
public class OptionMapper {

	public OptionDto entityToDTo(Option option) {
		
		OptionDto optionDto=new OptionDto();
		optionDto.setOptionId(option.getOptionId());
		optionDto.setOptionName(option.getOptionName());
		optionDto.setOptionType(option.getOptionType());
		optionDto.setUnit(option.getUnit());
		optionDto.setPrice(option.getPrice());
		
		return optionDto;
	}
	
	public Option dtoToEntity(OptionDto optionDto) {
		Option option=new Option();
		option.setOptionId(optionDto.getOptionId());
		option.setOptionName(optionDto.getOptionName());
		option.setOptionType(optionDto.getOptionType());
		option.setUnit(optionDto.getUnit());
		option.setPrice(optionDto.getPrice());
		return option;
	}
}
