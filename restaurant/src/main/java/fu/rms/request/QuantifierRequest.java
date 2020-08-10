package fu.rms.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuantifierRequest {

	
	private Long quantifierId;
	
	@NotNull(message = "Định lượng không thể để trống")
	@Positive(message = "Định lượng phải lớn hơn 0")
	private Double quantity;
	
	@NotNull(message = "Đơn giá* Định lượng(chi phí) không thể để trống")
	@Positive(message = "Đơn giá* Định lượng(chi phí) phải lớn hơn 0")
	private Double cost;
	
	@Size(max =200,message = "Mô tả tối đa là 200 kí tự")
	private String description;

	@NotNull(message = "Nguyên vật liệu không hợp lệ")
	private Long materialId;
}
