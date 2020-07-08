package fu.rms.newDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishOptionDtoNew {

	private Long orderDishOptionId;
	
	private Long optionId;
	
	private String optionName;
	
	private String optionType;
	
	private String optionUnit;
	
	private Integer quantity;
	
	private Double optionPrice;
	
	private Double sumPrice;
		
}
