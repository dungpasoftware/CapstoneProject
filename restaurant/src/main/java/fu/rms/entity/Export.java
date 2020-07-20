package fu.rms.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "export")
@Getter
@Setter
public class Export extends Auditable{
	
	@Id
	@Column(name="export_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long exportId;
	
	@Column(name="export_code")
	private String exportCode;
	
	@Column(name="export_date")
	private Timestamp exportDate;
	
	@Column(name="export_by")
	private Long exportBy;
	
	@Column(name="total_amount")
	private Double totalAmount;
	
	@Column(name="comment")
	private String comment;
	
	@OneToMany(mappedBy = "export")
	List<ExportMaterial> exportMaterials;
}
