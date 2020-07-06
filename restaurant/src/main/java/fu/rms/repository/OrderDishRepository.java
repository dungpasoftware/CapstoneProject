package fu.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.OrderDish;

public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {

	/*
	 * select by order: cả hoàn thành hoặc chưa hoàn thành
	 */
	@Query
	(value="SELECT * FROM order_dish o WHERE o.order_id = ?1", nativeQuery = true)
	List<OrderDish> findOrderDishByOrder(Long orderId);
	
	/*
	 * select by dish and status	
	 */
	@Query
	(value="SELECT * FROM order_dish o WHERE o.dish_id = :dish_id AND o.status = :status", nativeQuery = true)
	List<OrderDish> findOrderDishByDish(@Param("dish_id") Long dishId, @Param("status") Long status);
	
	/*
	 * select by order and status: hoàn thành
	 */
	@Query
	(value="SELECT * FROM order_dish o WHERE o.order_id = :order_id AND o.status = :status", nativeQuery = true)
	List<OrderDish> getOrderDishByOrderAndStatus(@Param("order_id") Long order_id, @Param("status") Long status);
	
	
	/**
	 * thêm 1 món mới
	 * @param orderId
	 * @param dishId
	 * @param quantity
	 * @param sellPrice
	 * @param status
	 * @return
	 */
	@Modifying
	@Transactional
	@Query
	(value="INSERT INTO order_dish (order_id, dish_id, quantity, sell_price, status_id)"
			+ "VALUES (:order_id, :dish_id, :quantity, :sellPrice, :status)", nativeQuery = true)
	int insertOrderDish(@Param("order_id") Long orderId, @Param("dish_id") Long dishId, 
			@Param("quantity") int quantity, @Param("sellPrice") double sellPrice, @Param("status") Long status);
	
	/**
	 * update khi trả món
	 * @param status
	 * @param orderDishId
	 * @return
	 */
	@Query
	(value="UPDATE order_dish o SET o.status = :status WHERE o.order_dish_id = :order_dish_id", nativeQuery = true)
	int updateStatusOrderDish(@Param("status") Long status, @Param("order_dish_id") Long orderDishId);
	
	/**
	 * update số lượng món (khi đã order xong)
	 * @param quantity
	 * @param sellPrice
	 * @param status
	 * @param orderDishId
	 * @return
	 */
	@Query
	(value="UPDATE order_dish o SET o.quantity = :quantity, o.sellPrice = :sellPrice,  o.status = :status "
			+ "WHERE o.order_dish_id = :order_dish_id", nativeQuery = true)
	int updateQuantityOrderDish(@Param("quantity") int quantity, @Param("sellPrice") double sellPrice, 
			@Param("status") Long status, @Param("order_dish_id") Long orderDishId);
	
	

	
}
