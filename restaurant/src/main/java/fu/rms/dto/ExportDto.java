package fu.rms.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportDto {

	private Long exportId;
	
	private String exportCode;
	
	private Timestamp exportDate;
	
	private Long exportBy;
	
	private Double totalAmount;
	
	private String comment;
	
	private List<ExportMaterialDto> exportMaterials;
}
