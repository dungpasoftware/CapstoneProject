package fu.rms.entity;

import java.util.List;

import javax.persistence.CascadeType;
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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "import")						// => import_export
@Getter
@Setter
public class Import extends Auditable {			//ImportExport
	
	@Id
	@Column(name="import_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long importId;					// import_export_id importExportId
	
	@Column(name="import_code")				// import_export_code
	private String importCode;				// => importExportCode
	
	@Column(name="type")
	private Integer type;				// => 2 types: 0: import / 1: export
	
	@Column(name="total_amount")		// total_price
	private Double totalAmount;			// totalPrice
	
	@Column(name="comment")
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="supplier_id")
	private Supplier supplier;				// chỉ import mới có
	
	@OneToMany(mappedBy = "imports",cascade = {CascadeType.PERSIST})
	List<ImportMaterial> importMaterials;			// ImportExportMaterial
	
}
