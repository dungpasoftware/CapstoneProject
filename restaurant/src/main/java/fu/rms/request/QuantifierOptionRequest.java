package fu.rms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuantifierOptionRequest {

	private Long quantifierOptionId;

	private Double quantity;

	private String unit;

	private Double cost;

	private String description;
	
	private Long materialId;
}	
