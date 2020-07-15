package fu.rms.newDto.mapper;

import org.springframework.stereotype.Component;

import fu.rms.newDto.OptionByDish;

@Component
public class OptionByDishMapper {

	public OptionByDish buildMapper(String optionName, Integer quantityOrderDishOption) {
		OptionByDish optionByDish = new OptionByDish();
		optionByDish.setOptionName(optionName);
		optionByDish.setQuantityOrderDishOption(quantityOrderDishOption);
		return optionByDish;
	}
}
