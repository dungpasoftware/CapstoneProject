package fu.rms.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionRequest {

	@NotEmpty(message = "Tên không thể để trống")
	private String optionName;
	
	@NotEmpty(message = "Hình thức không hợp lệ")
	private String optionType;
	
	@NotEmpty(message = "Đơn vị không thể để trống")
	private String unit;
	
	@NotNull(message = "Giá nhập không thể để trống")
	@Positive(message = "Giá nhập phải lớn hơn 0")
	private Double price;
	
	@NotNull(message = "Giá thành phẩm không thể để trống")
	@PositiveOrZero(message = "Giá thành phẩm phải lớn hơn hoặc bằng 0")
	private Double cost;
	
	@NotNull(message = "Giá bán không thể để trống")
	@PositiveOrZero(message = "Giá bán phải lớn hơn hoặc bằng 0")
	private Double optionCost;
	
	@Valid
	private List<QuantifierOptionRequest> quantifierOptions;
	
}