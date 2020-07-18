package fu.rms.dto;

import java.util.List;

import fu.rms.newDto.DishDtoNew;
import fu.rms.newDto.OrderDishOptionDtoNew;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishDto {

	private Long orderDishId;
	
	private Integer quantity;
	
	private Double sellPrice;
	
	private Double sumPrice;
	
	private String comment;
	
	private Long orderOrderId;
	
	private Integer quantityCancel;
	
	private Integer quantityOk;
	
	private Long statusStatusId;
	
	private String statusStatusValue;
	
	private DishDtoNew dish; // dishdto for orderdish
	
	private List<OrderDishOptionDtoNew> orderDishOptions;
	
}
