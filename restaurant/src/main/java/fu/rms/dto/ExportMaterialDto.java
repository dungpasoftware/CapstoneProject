package fu.rms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportMaterialDto {

	private Long exportMaterialId;
	
	private Double quantity;
	
	private Double price;
	
	private Double sumPrice;
	
	private String materialName;
	
	private String materialUnit;
}
