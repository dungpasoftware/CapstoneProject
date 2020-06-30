package fu.rms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDto {

	private Long dishId;
	
	private String dishCode;
	
	private String dishName;
	
	private String dishUnit;
	
	private double defaultPrice;
	
	private double cost;
	
	private int remainQuantity;
	
	private String description;
	
	private float timeComplete;
	
	private float timeNotification;
	
	private String image_url;
	
	private String statusId;
	
	private String statusValue;
}
