package fu.rms.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishRequest {
	
	private String dishCode;
	
	private String dishName;
	
	private String dishUnit;
	
	private Double defaultPrice;
	
	private Double cost;
	
	private Double dishCost;
	
	private String description;
	
	private Float timeComplete;
	
	private Float timeNotification;
	
	private String imageUrl;
	
	private Boolean typeReturn;
	
	private Long [] categoryIds;
	
	private Long [] optionIds;
	
	List<QuantifierRequest> quantifiers;
	

}
