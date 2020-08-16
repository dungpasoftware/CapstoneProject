package fu.rms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialQuantifierDto {

	private Long materialId;	
	private String materialName;	
	private String unit;		// theo đơn vị xuất	
	private Double unitPrice;
}
