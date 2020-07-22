package fu.rms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

	private String categoryName;
	
	private String description;
	
	private String imageUrl;
	
	private Integer priority;
}
