package fu.rms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fu.rms.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {

	
	//get all dish by status of dish
	@Query(name="Dish.findByStatusId")
	List<Dish> findByStatusId(Long statusId);
	
	// get all dish by categoryId
	@Query(name="Dish.findByCategoryId")
	List<Dish> findByCategoryId(Long categoryId);

	// get all dish by category and status of dish
	@Query(name = "Dish.findByCategoryIdAndStatusId")
	List<Dish> findByCategoryIdAndStatusId(Long categoryId, Long statusId);
	
	@Query(name = "Dish.findByMaterialId")
	List<Dish> findByMaterialId(Long materialId);
	
	Dish findByDishCode(String dishCode);
	
	@Query(value = "SELECT DISTINCT d.* " + 
			"FROM dishes AS d " + 
			"LEFT JOIN dish_category AS dc ON d.dish_id = dc.dish_id " + 
			"WHERE " + 
			"CASE WHEN :dishCode IS NOT NULL THEN d.dish_code like CONCAT('%' ,:dishCode, '%') ELSE 1=1 END " + 
			"AND " + 
			"CASE WHEN :categoryId IS NOT NULL THEN dc.category_id = :categoryId ELSE 1=1 END " + 
			"AND " + 
			"d.status_id = :statusId",
			countQuery = "SELECT COUNT(DISTINCT d.dish_id) " + 
					"FROM dishes AS d " + 
					"LEFT JOIN dish_category AS dc ON d.dish_id = dc.dish_id " + 
					"WHERE " + 
					"CASE WHEN :dishCode IS NOT NULL THEN d.dish_code like CONCAT('%' ,:dishCode, '%') ELSE 1=1 END " + 
					"AND " + 
					"CASE WHEN :categoryId IS NOT NULL THEN dc.category_id = :categoryId ELSE 1=1 END " + 
					"AND " + 
					"d.status_id = :statusId",
			nativeQuery = true)
	Page<Dish> search(String dishCode,Long categoryId,Long statusId,Pageable pageable);

}
