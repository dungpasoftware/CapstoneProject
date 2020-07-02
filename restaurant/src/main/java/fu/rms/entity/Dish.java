package fu.rms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
	@Column(name="dish_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long dishId;
	
	@Column(name="dish_code")
	private String dishCode;
	
	@Column(name="dish_name")
	private String dishName;
	
	@Column(name="dish_unit")
	private String dishUnit;
	
	@Column(name="default_price")
	private Double defaultPrice;
	
	@Column(name="cost")
	private Double cost;
	
	@Column(name="remain_quantity")
	private Integer remainQuantity;
	
	@Column(name="description",columnDefinition = "TEXT")
	private String description;
	
	@Column(name="time_complete")
	private Float timeComplete;
	
	@Column(name="time_notification")
	private Float timeNotification;
	
	@Column(name="image_url")
	private String image_url;
	
	@ManyToOne
	@JoinColumn(name="status_id")
	private Status status;

	@ManyToMany
	@JoinTable(name="dish_category",
	joinColumns = @JoinColumn(name="dish_id"),
	inverseJoinColumns = @JoinColumn(name="category_id"))
	private List<Category> categories;
	
	@OneToMany(mappedBy = "dish")
	List<Option> options;
}
