package fu.rms.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishDto {

	private Long orderDishId;
	
	private int quantity;
	
	private double sellPrice;
	
//	private String modifiedBy;
//	
//	private Date modifiedDate;
	
	private Long statusId;
	
	private String statusValue;
	
	private Long orderId;
	
	private String orderCode;
	
	private Long dishId;
	
	private String dishName;
	
	private String dishUnit;
	
	private double defaultPrice;
}
