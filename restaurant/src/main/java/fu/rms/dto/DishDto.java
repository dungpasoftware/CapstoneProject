package fu.rms.dto;

import java.util.List;

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
	
	private Double defaultPrice;	// giá bán
	
	private Double cost;			// chi phí nguyên vật liệu
	
	private Double dishCost;		// chi phí giá thành sản phẩm
	
	private Integer remainQuantity;
	
	private String description;
	
	private Float timeComplete;			// đơn vị giờ, ví dụ: chủ nhập theo đơn vị phút (45 phút thì quy ra đơn vị giờ để lưu: 0,75)
	
	private Float timeNotification;		// đơn vị giờ
	
	private String imageUrl;
	
	private Boolean typeReturn;
	

	private StatusDish status;
	
	private List<CategoryDish> categories;
	
	private List<OptionDto> options;

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
