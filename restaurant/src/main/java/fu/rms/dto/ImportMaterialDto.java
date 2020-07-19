package fu.rms.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportMaterialDto {

	private Long importMaterialId;
	
	private Double quantityImport;
	
	private Double quantityExport;
	
	private Double price;
	
	private Double sumPrice;
	
	private Timestamp expireDate;

	private Long importId;
	
	private MaterialDto material;
	
}
