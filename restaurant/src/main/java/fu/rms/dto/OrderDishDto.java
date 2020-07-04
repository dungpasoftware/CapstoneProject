package fu.rms.dto;

import java.util.Date;
import java.util.List;

import fu.rms.interfacedto.DishDto;
import fu.rms.interfacedto.OrderDishOptionDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishDto {

	private Long orderDishId;
	
	private Integer quantity;
	
	private Double sellPrice;
	
	private Long statusStatusId;
	
	private String statusStatusValue;
	
	private Long orderOrderId;
	
	private String orderCode;
	
	private String orderStatusValue;
	
	private DishDto dish; // dishdto for orderdish
	
	private List<OrderDishOptionDto> orderDishOptions;
	
}
