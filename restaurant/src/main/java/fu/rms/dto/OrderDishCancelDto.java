package fu.rms.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishCancelDto {

	private Long orderDishCancelId;
	
	private Integer quantityCancel;
	
	private String commentCancel;
	
	private String cancelBy;
	
	private Date cancelDate;
	
	private Long orderDishId;
}
