package fu.rms.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportMaterialRequest {

	@NotNull(message = "Số lượng nhập không thể để trống")
	@Positive(message = "Số lượng nhập phải lớn hơn 0")
	private Double quantityImport;
	
	@NotNull(message = "Đơn giá * số lượng(tổng giá nhập) khổng thể để trống")
	@Positive(message = "Đơn giá * số lượng(tổng giá nhập) phải lớn 0")
	private Double sumPrice;
	
	@PositiveOrZero(message = "Ngày hết hạn phải lớn hơn hoặc bằng 0")
	private Integer expireDate;
	
	private Long warehouseId;
	
	@Valid
	@NotNull(message = "Dữ liệu không hợp lệ")
	private MaterialRequest material;
	
}
