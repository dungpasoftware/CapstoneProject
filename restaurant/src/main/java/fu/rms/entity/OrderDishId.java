package fu.rms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishId implements Serializable {
	@Column(name = "order_id")
	private Long orderId;
	
	@Column(name = "dish_id")
	private Long dishId;

}
