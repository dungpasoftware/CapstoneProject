package fu.rms.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "order_dish")
@Data
public class OrderDish {

	@EmbeddedId
	private OrderDishId orderDishId;
	
	@ManyToOne
	@MapsId("orderId")
	private Order order;
	
	@ManyToOne
	@MapsId("dishId")
	private Dish dish;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="status")
	private Long status;
	
	@Column(name="sell_price")
	private double sellPrice;
	
}
