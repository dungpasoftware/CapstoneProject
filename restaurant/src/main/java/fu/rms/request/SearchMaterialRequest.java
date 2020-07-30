package fu.rms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchMaterialRequest {

	private String materialCode;
	
	private Long groupId;
	
	private Integer page;
}
