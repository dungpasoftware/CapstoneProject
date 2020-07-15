package fu.rms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "warehouses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {
	
	@Id
	@Column(name="warehouse_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long warehouseId;
	
	@Column(name="name")
	private String name;
}
