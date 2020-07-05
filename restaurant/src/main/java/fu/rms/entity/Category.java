package fu.rms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
	@Column(name="category_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long categoryId;
	
	@Column(name="category_name")
	private String categoryName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="image_url")
	private String imageUrl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="status_id")
	private Status status;
	
	@Column(name="priority")
	private Integer priority;
	
	@ManyToMany(mappedBy = "categories")
	private List<Dish> dishes;
	
	
	
}
