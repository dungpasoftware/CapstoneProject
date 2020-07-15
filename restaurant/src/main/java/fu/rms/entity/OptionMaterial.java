package fu.rms.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "quantifier_option")
public class OptionMaterial {

	@EmbeddedId
	OptionMaterialKey id;

	@Column(name="quantity")
	private Double quantity;
	
	@Column(name="unit")
	private String unit;
	
	@Column(name="cost")
	private Double cost;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne
    @MapsId("option_id")
    @JoinColumn(name = "option_id")
	private Option option;
	
	@ManyToOne
    @MapsId("material_id")
    @JoinColumn(name = "material_id")
    private Material material;
	
}
