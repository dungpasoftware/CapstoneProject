package fu.rms.newDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishOptionChef {
	
	private String optionName;
	
	private String optionType;
	
	private String optionUnit;
	
	private Integer quantity;
}
