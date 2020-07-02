package fu.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fu.rms.entity.OrderDish;

public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {

	@Query
	(value="SELECT * FROM order_dish o WHERE o.order_id = ?1", nativeQuery = true)
	List<OrderDish> getOrderDishByOrder(Long orderId);
	
	@Query
	(value="UPDATE order_dish o SET o.status = :status WHERE o.order_dish_id = :order_dish_id", nativeQuery = true)
	int updateStatusOrderDish(@Param("status") Long status, @Param("order_dish_id") Long orderDishId);
	
	@Query
	(value="UPDATE order_dish o SET o.quantity = :quantity, o.sellPrice = :sellPrice,  o.status = :status "
			+ "WHERE o.order_dish_id = :order_dish_id", nativeQuery = true)
	int updateQuantityOrderDish(@Param("quantity") int quantity, @Param("sellPrice") double sellPrice, 
			@Param("status") Long status, @Param("order_dish_id") Long orderDishId);
	
	@Query
	(value="INSERT INTO order_dish (order_id, dish_id, quantity, sellPrice, status)"
			+ "VALUES (:order_id, :dish_id, :quantity, :sellPrice, :status)", nativeQuery = true)
	int insertOrderDish(@Param("order_id") Long orderId, @Param("dish_id") Long dishId, 
			@Param("quantity") int quantity, @Param("sellPrice") double sellPrice, @Param("status") Long status);
	
}
