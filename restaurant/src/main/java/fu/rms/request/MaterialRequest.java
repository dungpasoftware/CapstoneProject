package fu.rms.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialRequest {
	
	@NotEmpty(message = "Mã NVL không thể để trống")
	@Size(max = 150,message = "Mã NVL tối đa là 150 kí tự")
	private String materialCode;
	
	@NotEmpty(message = "Tên NVL không thể để trống")
	@Size(max = 100,message = "Tên NVL tối đa là 150 kí tự")
	private String materialName;
	
	@NotEmpty(message = "Đơn vị không thể để trống")
	@Size(max = 50,message = "Đơn vị tối đa là 50 kí tự")
	private String unit;
	
	@NotNull(message = "Giá nhập không thể để trống")
	@Positive(message = "Giá nhập phải lớn hơn 0")
	private Double unitPrice;
	
	@PositiveOrZero(message = "hàng tồn tối thiểu phải lớn hơn hoặc bằng 0")
	private Double remainNotification;
	
	private Long groupMaterialId;
	
}
