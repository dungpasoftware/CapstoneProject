package fu.rms.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishRequest {
	
	@NotEmpty(message = "Mã thực đơn không thể để trống")
	@Size(max = 150, message = "Mã thực đơn tối đa là 150 kí tự")
	private String dishCode;
	
	@NotEmpty(message = "Tên thực đơn không thể để trống")
	@Size(max = 100, message = "Tên thực đơn tối đa là 100 kí tự")
	private String dishName;
	
	@NotEmpty(message = "Đơn vị không thể để trống")
	@Size(max = 50,message = "Đơn vị tối đa là 50 kí tự")
	private String dishUnit;
	
	@NotNull(message = "Giá bán không thể để trống")
	@Positive(message = "Giá bán tối thiểu phải lớn hơn 0")
	private Double defaultPrice;
	
	@NotNull(message = "Giá nhập không thể để trống")
	@PositiveOrZero(message = "Giá nhập phải lớn hơn hoặc bằng 0")
	private Double cost;
	
	@NotNull(message = "Giá thành không thể để trống")
	@PositiveOrZero(message = "Giá thành phải lớn hơn hoặc bằng 0")
	private Double dishCost;
	
	@Size(max = 200, message = "Mô tả món ăn  tối đa là 200 kí tự")
	private String description;
	
	@Min(value = 0,message = "Thời gian hoàn thành phải lớn hơn hoặc bằng 0")
	private Float timeComplete;
	
	//check sau
	private String imageUrl;
	
	@NotNull(message = "Loại sản phẩm không thể để trống")
	private Boolean typeReturn;
	
	private Long [] categoryIds;
	
	private Long [] optionIds;
	
	@Valid
	@NotEmpty(message = "Chưa chọn nguyên vật liệu")
	private List<QuantifierRequest> quantifiers;
	

}
