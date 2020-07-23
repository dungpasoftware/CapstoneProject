package fu.rms.newDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishInOrderDish {

	private Long orderDishId;
	
	private Integer quantity;
	
	private Long dishId;
}
