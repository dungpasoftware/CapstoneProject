package fu.rms.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import fu.rms.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query(name = "Category.findByDishId")
	List<Category> findByDishId(Long dishId);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM dish_category WHERE category_id = :categoryId", nativeQuery = true)
	void deleteByCategoryId(Long categoryId);
}
