package fu.rms.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportDto {

	private Long importId;
	
	private String importCode;
	
	private Double totalAmount;
	
	private String comment;
	
    private String createdBy;

    private Timestamp creationDate;
    
    private String lastModifiedBy;
    
    private Timestamp lastModifiedDate;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private WarehouseDto warehouse;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private SupplierDto supplier;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	List<ImportMaterialDto> importMaterials;
	
}
