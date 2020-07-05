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
	@Query(name="Dish.findByCategoryId")
	List<Dish> findByCategoryId(Long categoryId);

	// get all dish by category and list of statuses
	@Query(name = "Dish.findByCategoryIdAndStatusIn")
	List<Dish> findByCategoryIdAndStatusIn(Long categoryId, Collection<Long> statuses);

	// when ordered dish, update remainQuantity
	@Modifying
	@Transactional
	@Query(name = "Dish.updateRemainQuantity")
	int updateRemainQuantity(Long dishId, int remainQuantity);

	// update status of dish
	@Modifying
	@Transactional
	@Query(name = "Dish.updateStatus")
	int updateStatus(Long dishId, int statusId);
}
