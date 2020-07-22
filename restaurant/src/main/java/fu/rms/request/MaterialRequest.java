package fu.rms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialRequest {
	
	private Long materialId;

	private String materialCode;
	
	private String materialName;
	
	private String unit;
	
	private Double remainNotification;
	
	private Long groupMaterialId;
	
}
