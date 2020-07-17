package fu.rms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuantifierDto {

	private Long quantifierId;
	
	private Double quantity;
	
	private String unit;
	
	private Double cost;
	
	private String description;
	
	private Long materialId;
	
	private String materialName;
}
