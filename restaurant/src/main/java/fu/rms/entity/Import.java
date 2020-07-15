package fu.rms.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "import")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Import {
	
	@Id
	@Column(name="import_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long importId;
	
	@Column(name="import_code")
	private String importCode;
	
	@Column(name="import_date")
	private Timestamp importDate;
	
	@Column(name="import_by")
	private Long importBy;
	
	@Column(name="total_amount")
	private Double totalAmount;
	
	@Column(name="comment")
	private String comment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="warehouse_id")
	private Warehouse warehouse;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="supplier_id")
	private Supplier supplier;
	
	@OneToMany(mappedBy = "imports")
	List<ImportMaterial> importMaterials;
}
