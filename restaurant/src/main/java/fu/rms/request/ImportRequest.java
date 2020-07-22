package fu.rms.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportRequest {

	private Double totalAmount;
	
	private String comment;
	
	private Long supplierId;
		
	private List<ImportMaterialRequest> importMaterials;
}
