package fu.rms.request;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishRequest {
	
	private String dishCode;
	
	@NotEmpty(message = "Tên thực đơn không thể để trống")
	private String dishName;
	
	private String dishUnit;
	
	@NotNull(message = "Giá bán không thể để trống")
	@Min(value = 1,message = "Giá bán tối thiểu phải lớn hơn 0")
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
