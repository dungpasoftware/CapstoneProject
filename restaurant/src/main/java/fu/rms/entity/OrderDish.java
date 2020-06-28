package fu.rms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "order_dish")
@Data
public class OrderDish {

	@Id
	@Column(name = "order_dish_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long orderDishId;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name="dish_id")
	private Dish dish;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="status")
	private Long status;
	
	@Column(name="sell_price")
	private double sellPrice;
	
	@OneToMany(mappedBy = "orderDish")
	List<OrderDishOption> orderDishOptions;
}
