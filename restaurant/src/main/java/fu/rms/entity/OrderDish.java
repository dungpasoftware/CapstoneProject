package fu.rms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="dish_id")
	private Dish dish;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@Column(name="modified_date")
	private Date modifiedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="status_id")
	private Status status;
	
	@Column(name="sell_price")
	private Double sellPrice;
	
	@Column(name="sum_price")
	private Double sumPrice;
	
	@OneToMany(mappedBy = "orderDish")
	List<OrderDishOption> orderDishOptions;
}
