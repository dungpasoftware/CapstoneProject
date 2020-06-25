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
@Table(name = "tables")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tables {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="table_code")
	private String tableCode;
	
	@Column(name="table_name")
	private String tableName;
	
	@ManyToOne
	@JoinColumn(name="location_id")
	private LocationTable locationTable;
	
	@Column(name="min_capacity")
	private int minCapacity;
	
	@Column(name="max_capacity")
	private int maxCapacity;
	
	@Column(name="status")
	private Long status;
	
	@OneToMany(mappedBy = "table")
	private List<Order> orders;
	
	
	
	
	
	
}
