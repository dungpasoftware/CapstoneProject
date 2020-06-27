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
public class DishOptionTypeId implements Serializable {
	
	@Column(name="dish_id")
	private Long dishId;
	
	@Column(name="option_type_id")
	private Long optionTypeId;
}
