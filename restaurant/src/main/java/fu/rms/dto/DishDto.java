package fu.rms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private StatusDish status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<CategoryDish> categories;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<OptionDish> options;
	
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class StatusDish{
		private Long statusId;
		private String statusValue;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CategoryDish{
		private Long categoryId;
		private String categoryName;
		private String imageUrl;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class OptionDish{
		private Long optionId;
		private String optionName;
	}

}
