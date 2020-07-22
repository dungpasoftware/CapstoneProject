package fu.rms.newDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishOptionDtoNew { // for orderDish

	private Long orderDishOptionId;
	
	private Long optionId;
	
	private String optionName;
	
	private String optionType;
	
	private String optionUnit;
	
	private Integer quantity;
	
	private Double optionPrice;
	
	private Double sumPrice;
		
}
