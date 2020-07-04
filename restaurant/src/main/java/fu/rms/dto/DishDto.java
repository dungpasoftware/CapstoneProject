package fu.rms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
	
	private String imageUrl;
	
	private StatusDish status;
	
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class StatusDish{
		private Long statusId;
		private String statusValue;
	}
}
