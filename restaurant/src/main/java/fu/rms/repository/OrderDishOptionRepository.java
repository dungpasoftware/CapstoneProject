package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.OrderDishOption;

public interface OrderDishOptionRepository extends JpaRepository<OrderDishOption, Long>{

	@Modifying
	@Transactional
	@Query
	(value="INSERT INTO order_dish_option (order_dish_id, option_id, quantity, sum_price, unit_price,  status_id)"
			+ " VALUES (:order_dish_id, :option_id, :quantity, :sum_price, :unit_price, :status)", nativeQuery = true)
	int insertOrderDishOption(@Param("order_dish_id") Long orderDishId, @Param("option_id") Long optionId, 
			@Param("quantity") Integer quantity, @Param("sum_price") Double sumPrice, @Param("unit_price") Double unitPrice,
			@Param("status") Long status);
}
