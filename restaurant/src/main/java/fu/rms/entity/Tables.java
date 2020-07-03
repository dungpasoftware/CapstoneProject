package fu.rms.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	@Column(name="table_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tableId;
	
	@Column(name="table_code")
	private String tableCode;
	
	@Column(name="table_name")
	private String tableName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="location_id")
	private LocationTable locationTable;
	
	@Column(name="min_capacity")
	private Integer minCapacity;
	
	@Column(name="max_capacity")
	private Integer maxCapacity;	
	
	@ManyToOne
	@JoinColumn(name="status_id")
	private Status status;
	
	@OneToOne
	@JoinColumn(name="order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name="staff_id")
	private Staff staff;
}
