package fu.rms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "dish_option")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishOption {

	@Id
	@Column(name="dish_option_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long dishOptionId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="dish_id")
	private Dish dish;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="option_id")
	private Option option;
	
}
