package fu.rms.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionRequest {

	private String optionName;
	
	private String optionType;
	
	private String unit;
	
	private Double price;
	
	private Double cost;
	
	private Double optionCost;
	
	private List<QuantifierOptionRequest> quantifierOptions;
	
}
