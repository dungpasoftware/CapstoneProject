package fu.rms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class OptionDto {

	private Long optionId;
	
	private String optionName;
	
	private String optionType;
	
	private String unit;
	
	private Double price;
	
	private OptionStatus status;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class OptionStatus{
		private Long statusId;
		private String statusValue;
	}
	
}
