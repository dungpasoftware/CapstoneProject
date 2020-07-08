package fu.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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
	
	// update status of dish
	@Modifying
	@Transactional
	@Query(name = "Dish.updateStatusId")
	int updateStatus(Long dishId, Long statusId);

	// when ordered dish, update remainQuantity
	@Modifying
	@Transactional
	@Query(name = "Dish.updateRemainQuantity")
	int updateRemainQuantity(Long dishId, int remainQuantity);

}
