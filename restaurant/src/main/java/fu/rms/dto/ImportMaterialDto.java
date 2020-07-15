package fu.rms.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportMaterialDto {

	private Long importMaterialId;
	
	private Double quantity;
	
	private Double price;
	
	private Double sumPrice;
	
	private Timestamp expireDate;

	private Long importId;
	
	private Long materialId;
	
	private String materialName;
	
	private String materialUnit;
}
