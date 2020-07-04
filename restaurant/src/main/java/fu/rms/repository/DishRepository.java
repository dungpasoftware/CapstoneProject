package fu.rms.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {

	// get all dish by categoryId
	@Query(value = "SELECT d.* FROM dishes AS d " + "INNER JOIN dish_category AS dc ON d.dish_id = dc.dish_id "
			+ "WHERE dc.category_id = :categoryId AND d.remain_quantity>0", nativeQuery = true)
	List<Dish> findByCategoryId(Long categoryId);

	// get all dish by category and list of statuses
	@Query(value = "SELECT d.* FROM dishes AS d " + "INNER JOIN dish_category AS dc ON d.dish_id = dc.dish_id "
			+ "WHERE dc.category_id = :categoryId AND d.status_id IN(:statuses) AND d.remain_quantity>0", nativeQuery = true)
	List<Dish> findByCategoryIdAndStatusIn(Long categoryId, Collection<Long> statuses);

	// when ordered dish, update remainQuantity
	@Modifying
	@Transactional
	@Query(value = "Update dishes AS d SET d.remain_quantity = :remainQuantity "
			+ "WHERE d.dish_id = :dishId", nativeQuery = true)
	int updateRemainQuantity(Long dishId, int remainQuantity);

	// update status of dish
	@Modifying
	@Transactional
	@Query(value="Update dishes AS d SET d.status_id = :statusId "
			+ "WHERE d.dish_id = :dishId", nativeQuery = true)
	int updateStatus(Long dishId, int statusId);
}
