package fu.rms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class QuantifierOptionDto {

	private Long quantifierOptionId;

	private Double quantity;

	private String unit;

	private Double cost;

	private String description;
	
	private MaterialQuantifierOption material;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MaterialQuantifierOption{
		private Long materialId;
		private String materialName;
		private String unit;		// theo đơn vị xuất
		private double unitPrice;	// theo đơn vị xuất
	}

}
