package fu.rms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dishes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="dish_code")
	private String dishCode;
	
	@Column(name="dish_name")
	private String dishName;
	
	@Column(name="dish_unit")
	private String dishUnit;
	
	@Column(name="default_price")
	private double defaultPrice;
	
	@Column(name="cost")
	private double cost;
	
	@Column(name="remain_quantity")
	private int remainQuantity;
	
	@Column(name="description",columnDefinition = "TEXT")
	private String description;
	
	@Column(name="time_complete")
	private float timeComplete;
	
	@Column(name="time_notification")
	private float timeNotification;
	
	@Column(name="image_url")
	private String image_url;
	
	@Column(name="status")
	private Long status;
	
	@OneToMany(mappedBy = "dish")
	List<OrderDish> orderDishes;
	
	@ManyToMany
	@JoinTable(name="dish_category",
	joinColumns = @JoinColumn(name="dish_id"),
	inverseJoinColumns = @JoinColumn(name="category_id"))
	private List<Category> categories;
	
	@OneToMany(mappedBy = "dish")
	List<DishOptionType> dishOptionTypes;
}
