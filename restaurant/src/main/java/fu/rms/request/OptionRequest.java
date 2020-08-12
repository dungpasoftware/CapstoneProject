package fu.rms.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionRequest {

	@NotEmpty(message = "Tên không thể được trống")
	@Size(max = 100, message = "Tên tối đa 150 kí tự")
	private String optionName;
	
	@NotEmpty(message = "Hình thức không hợp lệ")
	@Size(max = 10, message = "Hình thức tối đa 10 kí tự")
	private String optionType;
	
	@NotEmpty(message = "Đơn vị không thể để trống")
	@Size(max = 50, message = "Đơn vị tối đa 50 kí tự")
	private String unit;
	
	@NotNull(message = "Giá nhập không được trống")
	@Positive(message = "Giá nhập phải lớn hơn 0")
	@Digits(integer = 15,fraction = 0,message = "Giá nhập tối đa 15 ký tự số")
	private Double price;
	
	@NotNull(message = "Giá thành phẩm không được trống")
	@Positive(message = "Giá thành phẩm phải lớn hơn 0")
	@Digits(integer = 15,fraction = 0,message = "Giá thành tối đa 15 ký tự số")
	private Double cost;
	
	@NotNull(message = "Giá bán không được trống")
	@Positive(message = "Giá bán phải lớn hơn 0")
	@Digits(integer = 15,fraction = 0,message = "Giá bán tối đa 15 ký tự số")
	private Double optionCost;
	
	@Valid
	@NotEmpty(message = "Chưa chọn nguyên vật liệu")
	private List<QuantifierOptionRequest> quantifierOptions;
	
}
