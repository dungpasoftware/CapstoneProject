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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "materials")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Material {

	@Id
	@Column(name="material_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long materialId;
	
	@Column(name = "material_code")
	private String materialCode;
	
	@Column(name = "material_name")
	private String materialName;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "unit_price")
	private Double unitPrice;
	
	@Column(name = "total_import")
	private Double totalImport;
	
	@Column(name = "total_export")
	private Double totalExport;
	
	@Column(name = "remain")
	private Double remain;
	
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
	List<OptionMaterial> optionQuantifiers;
}
