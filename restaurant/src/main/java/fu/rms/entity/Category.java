package fu.rms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="category_name")
	private String categoryName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="image_url")
	private String imageUrl;
	
	@Column(name="status")
	private Long status;
	
	@Column(name="priority")
	private int priority;
	
	@ManyToMany(mappedBy = "categories")
	private List<Dish> dishes;
	
	
	
}
