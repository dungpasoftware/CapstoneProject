package fu.rms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OptionMaterialKey implements Serializable{

	@Column(name="option_id")
	Long optionId;
	
	@Column(name="material_id")
	Long materialId;
}
