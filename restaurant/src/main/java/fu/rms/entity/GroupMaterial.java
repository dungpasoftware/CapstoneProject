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
@Table(name = "group_material")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMaterial {

	@Id
	@Column(name="group_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long groupId;
	
	@Column(name = "group_name")
	private String groupName;
}
