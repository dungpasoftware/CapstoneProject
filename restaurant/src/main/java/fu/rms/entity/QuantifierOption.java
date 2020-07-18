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
@Table(name = "quantifier_option")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantifierOption {

	@Id
	@Column(name="quantifier_option_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long quantifierOptionId;
	
	@Column(name="quantity")
	private Double quantity;
	
	@Column(name="unit")
	private String unit;
	
	@Column(name="cost")
	private Double cost;
	
	@Column(name="description")
	private String description;
		
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
	private Option option;
	
}
