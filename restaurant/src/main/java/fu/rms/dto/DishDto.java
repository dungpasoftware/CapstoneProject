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
	
	private Double defaultPrice;
	
	private Double cost;
	
	private Integer remainQuantity;
	
	private String description;
	
	private Float timeComplete;
	
	private Float timeNotification;
	
	private String imageUrl;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private StatusDish status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<CategoryDish> categories;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<OptionDto> options;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<QuantifierDto> quantifiers;
	
	
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
	

}
