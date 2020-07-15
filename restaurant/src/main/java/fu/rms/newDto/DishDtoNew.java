package fu.rms.newDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDtoNew { // for orderdish
	
	private Long dishId;
	
	private String dishName;
	
	private String dishUnit;
	
	private Double defaultPrice;
}
