package fu.rms.interfacedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishOptionDto {

	private Long orderDishOptionId;
	
	private Long optionId;
	
	private String optionName;
	
	private String optionUnit;
	
	private Integer quantity;
	
	private Double optionPrice;
	
	private Double sumPrice;
		
}
