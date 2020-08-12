package fu.rms.request;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ImportExistMaterialRequest {

	@NotNull(message = "Số lượng nhập không được trống")
	@Positive(message = "Số lượng nhập phải lớn hơn 0")
	@Digits(integer = 15,fraction = 0,message = "Số lượng tối đa 15 ký tự số")
	private Double quantityImport;
	
	@NotNull(message = "Giá nhập không được trống")
	@Positive(message = "Giá nhập phải lớn hơn 0")
	@Digits(integer = 15,fraction = 0,message = "Giá nhập tối đa 15 ký tự số")
	private Double unitPrice;
	
	@NotNull(message = "Đơn giá * số lượng(tổng giá nhập) không được trống")
	@Positive(message = "Đơn giá * số lượng(tổng giá nhập) phải lớn 0")
	@Digits(integer = 15,fraction = 0,message = "Đơn giá * số lượng(tổng giá nhập) tối đa 15 ký tự số")
	private Double sumPrice;
	
	@PositiveOrZero(message = "Ngày hết hạn phải lớn hơn hoặc bằng 0")
	@Digits(integer = 5,fraction = 0,message = "Ngày hết hạn tối đa 5 ký tự số")
	private Integer expireDate;
	
	private Long warehouseId;
	
	private Long materialId;
}
