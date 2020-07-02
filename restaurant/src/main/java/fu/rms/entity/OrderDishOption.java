package fu.rms.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "order_dish_option")
@Data
public class OrderDishOption {

	@Id
	@Column(name="order_dish_option_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long orderDishOptionId;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@Column(name="unit_price")
	private Double unitPrice;
	
	@Column(name="sum_price")
	private Double sumPrice;
	
	@ManyToOne
	@JoinColumn(name="option_id")
	private Option option;
	
	@ManyToOne
	@JoinColumn(name="dish_id")
	private Dish dish;
	
	@ManyToOne
	@JoinColumn(name="order_dish_id")
	private OrderDish orderDish;
}
