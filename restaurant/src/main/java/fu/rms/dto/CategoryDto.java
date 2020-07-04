package fu.rms.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

	private Long categoryId;
	
	private String categoryName;
	
	private String description;
	
	private String imageUrl;
	
	private int priority;
	
	private List<DishDto> dishes;
	
	
}
