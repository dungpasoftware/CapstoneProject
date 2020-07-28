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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "import_material")		//import_export_material
@Getter
@Setter
public class ImportMaterial {			//ImportExportMaterial

	@Id
	@Column(name="import_material_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long importMaterialId;			// => importExportMaterialId
	
	@Column(name="quantity_import")			// số lượng (cả nhập và xuất)
	private Double quantityImport;			// => quantity
	
	@Column(name="price")					// giá giá nhập/xuất theo 1 đơn vị
	private Double price;					// => unitPrice, unit_price
	
	@Column(name="sum_price")				// tổng giá nhập/xuất cho 1 nvl
	private Double sumPrice;
	
	@Column(name="expire_date")
	private Timestamp expireDate;			// chỉ import mới có
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="import_id")
	private Import imports;					// ImportExport
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="material_id")
	private Material material;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="warehouse_id")
	private Warehouse warehouse;			// chỉ import mới có
}
