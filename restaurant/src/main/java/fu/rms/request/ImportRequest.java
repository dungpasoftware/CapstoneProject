package fu.rms.request;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportRequest {
	
	@NotEmpty(message = "Mã Phiếu không thể để trống")
	@Size(max = 150, message = "Mã phiếu tối đa là 150 kí tự")
	private String importCode;

	@NotNull(message = "Tổng giá không thể để trống")
	@Positive(message = "Tổng giá phải lớn hơn 0")
	private Double totalAmount;
	
	@Size(max = 200,message = "Ghi chú tối đa là 200 kí tự")
	private String comment;
	
	private Long supplierId;
		
	@Valid
	@NotNull(message = "Dữ liệu không hợp lệ")
	private ImportMaterialRequest importMaterial;
}
