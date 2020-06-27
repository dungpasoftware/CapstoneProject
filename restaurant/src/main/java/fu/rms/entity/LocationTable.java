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
@Table(name = "location_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationTable {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "location_code")
	private String location_code;
	
	@Column(name = "location_name")
	private String location_name;
	
	@Column(name="status")
	private Long status;
	
	@OneToMany(mappedBy = "locationTable")
	private List<Tables> tables;
}
