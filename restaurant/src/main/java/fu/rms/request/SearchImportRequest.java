package fu.rms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchImportRequest {

	private Long supplierId;
	
	private String dateFrom;
	
	private String dateTo;
	
	private Integer page;
	
}
