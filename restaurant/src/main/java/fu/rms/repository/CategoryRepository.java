package fu.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import fu.rms.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query(name = "Category.findByStatusId")
	List<Category> findByStatusId(Long statusId);
	
	@Modifying
	@Query(name= "Category.updateStatusId")
	void updateStatusId(Long categoryId, Long statusId);
}
