package fu.rms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportMaterialRequest {

	private Double quantityImport;
	
	private Double unitPrice;
	
	private Double sumPrice;
	
	private Integer expireDate;
	
	private Long warehouseId;
	
	private MaterialRequest material;
	
}
