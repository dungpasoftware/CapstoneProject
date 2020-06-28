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
@Table(name = "statuses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status {
	@Id
	@Column(name = "status_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long statusId;
	
	@Column(name = "status_name")
	private String statusName;
	
	@Column(name = "status_code")
	private int statusCode;
	
	@Column(name="status_value")
	private String statusValue;
	
	@Column(name="status_description")
	private String statusDescription;
	
	@Column(name="status_color")
	private String statusColor;
	
	@Column(name="status_icon")
	private String statusIcon;
}
