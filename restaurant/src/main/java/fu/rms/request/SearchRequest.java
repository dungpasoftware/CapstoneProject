package fu.rms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {

	private String dishCode;

	private Long categoryId;
	
	private Integer page;
}
