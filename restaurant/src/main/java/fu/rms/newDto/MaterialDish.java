package fu.rms.newDto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialDish {

	private Long materialId;
	
	private Double sumQuantity;
	
	private List<Long> listDishId;
}
