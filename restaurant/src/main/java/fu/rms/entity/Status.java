package fu.rms.entity;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "statuses")
@Getter
@Setter
public class Status {
	@Id
	@Column(name = "status_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long statusId;
	
	@Column(name = "status_name")
	private String statusName;
	
	@Column(name="status_value")
	private String statusValue;
	
	@Column(name="status_description")
	private String statusDescription;
	
	@Column(name="status_color")
	private String statusColor;
	
	@Column(name="status_icon")
	private String statusIcon;
	
	@OneToMany(mappedBy = "status")
	private List<Tables> tables;
	
	@OneToMany(mappedBy = "status")
	private List<LocationTable> locationTables;
	
	@OneToMany(mappedBy = "status")
	private List<Dish> dishes;
	
	@OneToMany(mappedBy = "status")
	private List<Order> orders;
	
	@OneToMany(mappedBy = "status")
	private List<OrderDish> orderDishes;
	
	@OneToMany(mappedBy = "status")
	private List<Option> options;
	
	@OneToMany(mappedBy = "status")
	private List<OrderDishOption> orderDishOption;
	
	@OneToMany(mappedBy = "status")
	private List<Category> categories;
}
