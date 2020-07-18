package fu.rms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialDto {

	private Long materialId;
	
	private String materialCode;
	
	private String materialName;
	
	private String unitImport; 	// đơn vị import: đơn vị lúc nhập
	
	private String unitExport; 	// đơn vị export: đơn vị lúc xuất ra
	
	private Double rating; 		// tỉ lệ chuyển đổi giữa 2 đơn vị
	
	private Double unitImportPrice;		// giá theo đơn vị import
	
	private Double unitExportPrice;		// giá theo đơn vị export
	
	private Double totalImport;			// theo đơn vị export
	
	private Double totalExport;			// theo đơn vị export
	
	private Double remain;				// theo đơn vị export
	
	private Double remainUnitImport;	// theo đơn vị import
	
	private Double remainNotifycation;
	
	private Long statusId;
	
	private String statusValue;
	
	private GroupDto groupMaterial;
	
}
