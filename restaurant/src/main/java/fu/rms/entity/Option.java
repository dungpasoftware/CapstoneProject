package fu.rms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "options")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Option {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="option_name")
	private String optionName;
	
	@Column(name="display_type")
	private String displayName;
	
	@OneToMany(mappedBy = "option")
	List<OptionType> optionTypes;
}
