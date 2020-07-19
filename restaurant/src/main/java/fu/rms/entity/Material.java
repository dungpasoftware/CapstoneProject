package fu.rms.entity;

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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "materials")
@Getter
@Setter
public class Material {

	@Id
	@Column(name="material_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long materialId;
	
	@Column(name = "material_code")
	private String materialCode;
	
	@Column(name = "material_name")
	private String materialName;
	
	@Column(name = "unit_import")		// đơn vị import: đơn vị lúc nhập
	private String unitImport;
	
	@Column(name = "unit_export")		// đơn vị export: đơn vị lúc xuất ra
	private String unitExport;
	
	@Column(name = "rating")			// tỉ lệ chuyển đổi giữa 2 đơn vị
	private Double rating;
	
	@Column(name = "unit_import_price")		// giá theo đơn vị import
	private Double unitImportPrice;
	
	@Column(name = "unit_export_price")		// giá theo đơn vị export
	private Double unitExportPrice;
	
	@Column(name = "total_import")	// theo đơn vị export
	private Double totalImport;
	
	@Column(name = "total_export")	// theo đơn vị export
	private Double totalExport;
	
	@Column(name = "remain")		// theo đơn vị export
	private Double remain;
	
	@Column(name = "remain_unit_import")	// theo đơn vị import
	private Double remainUnitImport;
	
	@Column(name = "remain_notifycation")		
	private Double remainNotifycation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="status_id")
	private Status status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_id")
	private GroupMaterial groupMaterial;
	
	@OneToMany(mappedBy = "material")
	List<ImportMaterial> importMaterials;
	
	@OneToMany(mappedBy = "material")
	List<Quantifier> quantifiers;
	
	@OneToMany(mappedBy = "material")
	List<QuantifierOption> quantifierOptions;
}
