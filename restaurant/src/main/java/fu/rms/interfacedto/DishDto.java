package fu.rms.interfacedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDto {
	
	private Long dishId;
	
	private String dishName;
	
	private String dishUnit;
	
	private Double defaultPrice;
}
