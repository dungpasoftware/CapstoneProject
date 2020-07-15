package fu.rms.dto;

import java.sql.Timestamp;
import java.util.List;

import fu.rms.entity.Supplier;
import fu.rms.entity.Warehouse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportDto {

	private Long importId;
	
	private String importCode;
	
	private Timestamp importDate;
	
	private Long importBy;
	
	private Double totalAmount;
	
	private String comment;
	
	private Warehouse warehouse;
	
	private Supplier supplier;
	
	List<ImportMaterialDto> importMaterials;
}
