package fu.rms.request;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class QuantifierRequest {

	
	private Long quantifierId;
	
	private Double quantity;
	
	private String unit;
	
	private Double cost;
	
	private String description;
	
	private Long materialId;
}
