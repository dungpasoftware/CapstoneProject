package fu.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fu.rms.entity.Category;
import fu.rms.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {

	@Query(value ="select d.* from dishes d inner join dish_category dc on d.dish_id = dc.dish_id where dc.category_id = :categoryId", nativeQuery = true )
	List<Dish> findByCategoryId(Long categoryId);
}
