package fu.rms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<DishDto> dishes;
	
	
	
}
