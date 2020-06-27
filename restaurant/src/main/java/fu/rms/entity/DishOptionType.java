package fu.rms.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "dish_option_type")
@Data
public class DishOptionType {
	
	@EmbeddedId
	private DishOptionTypeId dishOptionTypeId;
	
	@ManyToOne
	@MapsId("dishId")
	private Dish dish;
	
	@ManyToOne
	@MapsId("optionTypeId")
	private OptionType optionType;
}
