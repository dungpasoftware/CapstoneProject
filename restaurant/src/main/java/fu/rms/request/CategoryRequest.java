package fu.rms.request;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

	@NotEmpty(message = "Tên nhóm thực đơn không thể để trống")	
	private String categoryName;
	
	private String description;
	
	private String imageUrl;
	
	private Integer priority;
}
