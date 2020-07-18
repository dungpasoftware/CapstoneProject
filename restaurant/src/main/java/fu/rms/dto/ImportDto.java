package fu.rms.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Warehouse warehouse;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Supplier supplier;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	List<ImportMaterialDto> importMaterials;
	
//	@JSONINCLUDE(JSONINCLUDE.INCLUDE.NON_NULL)
//	LIST<MATERIALDTO> MATERIALS;
}
