package fu.rms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "import_material")
@Data
public class ImportMaterial {

	@Id
	@Column(name="import_material_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long importMaterialId;
	
	@Column(name="quantity_import")
	private Double quantityImport;
	
	@Column(name="quantity_export")
	private Double quantityExport;
	
	@Column(name="price")		// giá nhập
	private Double price;
	
	@Column(name="sum_price")		// tổng giá nhập
	private Double sumPrice;
	
	@Column(name="expire_date")
	private Timestamp expireDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="import_id")
	private Import imports;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="material_id")
	private Material material;
}
