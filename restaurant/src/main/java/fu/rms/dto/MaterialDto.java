package fu.rms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialDto {

	private Long materialId;
	
	private String materialCode;
	
	private String materialName;
	
	private String unit;
	
	private Double unitPrice;
	
	private Double totalImport;
	
	private Double totalExport;
	
	private Double remain;
	
	private Double remainNotifycation;
	
	private Long statusId;
	
	private String statusValue;
	
	private GroupDto groupMaterial;
	
}
