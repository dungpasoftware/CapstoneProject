package fu.rms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "option_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionType {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="option_id")
	private Option option;
	
	@Column(name="value")
	private String value;
	
	@Column(name="price")
	private double price;
	
	@Column(name="status")
	private Long status;
	
	@Column(name="priority")
	private int priority;
	
	@OneToMany(mappedBy = "optionType")
	List<DishOptionType> dishOptionTypes;
}
