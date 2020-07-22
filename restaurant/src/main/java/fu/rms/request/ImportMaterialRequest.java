package fu.rms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportMaterialRequest {

	private Double quantityImport;
	
	private Double price;
	
	private Double sumPrice;
	
	private Integer expireDate;
	
	private Long warehouseId;
	
	private MaterialRequest material;
	
}
