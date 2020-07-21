package fu.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.OrderDishOption;

public interface OrderDishOptionRepository extends JpaRepository<OrderDishOption, Long>{

	/*
	 * lấy các id của order_dish
	 * @param orderDishId
	 * @return
	 */
	@Query
	(value="SELECT odo.order_dish_option_id FROM order_dish_option odo WHERE odo.order_dish_id = orderDishId)", nativeQuery = true)
	List<Long> getIdByOrderDish(@Param("order_dish_id") Long orderDishId);
	
	/**
	 * thêm mới topping
	 * @param orderDishId
	 * @param optionId
	 * @param quantity
	 * @param sumPrice
	 * @param unitPrice
	 * @param status
	 * @return
	 */
	@Modifying
	@Transactional
	@Query
	(value="INSERT INTO order_dish_option (order_dish_id, option_id, quantity, sum_price, unit_price,  status_id)"
			+ " VALUES (:orderDishId, :optionId, :quantity, :sumPrice, :unitPrice, :statusId)", nativeQuery = true)
	int insertOrderDishOption(@Param("orderDishId") Long orderDishId, @Param("optionId") Long optionId, 
			@Param("quantity") Integer quantity, @Param("sumPrice") Double sumPrice, @Param("unitPrice") Double unitPrice,
			@Param("statusId") Long status);
	
	/*
	 * chỉnh sửa topping
	 * @param optionId
	 * @param quantity
	 * @param unitPrice
	 * @param sumPrice
	 * @param statusId
	 * @param orderDishOptionId
	 * @return
	 */
	@Modifying
	@Transactional
	@Query
	(value="UPDATE order_dish_option odo SET odo.option_id = :optionId, odo.quantity = :quantity, odo.unit_price = :unitPrice, "
			+ "odo.sum_price = :sumPrice, odo.status_id =:statusId WHERE odo.order_dish_option_id = :orderDishOptionId", nativeQuery = true)
	int updateOrderDishOption(@Param("optionId") Long optionId, @Param("quantity") Integer quantity, @Param("unitPrice") Double unitPrice,
			@Param("sumPrice") Double sumPrice, @Param("statusId") Long statusId, @Param("orderDishOptionId") Long orderDishOptionId);
	
	/*
	 * hủy topping canceled
	 * @param statusId
	 * @param orderDishOptionId
	 * @return
	 */
	@Modifying
	@Transactional
	@Query
	(value="UPDATE order_dish_option odo SET odo.status_id =:statusId WHERE odo.order_dish_id = :orderDishId", nativeQuery = true)
	int updateCancelOrderDishOption(@Param("statusId") Long statusId, @Param("orderDishId") Long orderDishId);
	
	/*
	 * delete khi orderdish chưa sử dụng nvl
	 */
	@Modifying
	@Transactional
	@Query
	(value="DELETE FROM order_dish_option WHERE order_dish_id = :orderDishId", nativeQuery = true)
	int deleteOrderDishOption(@Param("orderDishId") Long orderDishId);
	
	/*
	 * delete khi orderdish chưa sử dụng nvl
	 */
	@Modifying
	@Transactional
	@Query
	(value="DELETE FROM order_dish_option WHERE order_dish_option_id = :orderDishOptionId", nativeQuery = true)
	int deleteOrderDishOptionById(@Param("orderDishOptionId") Long orderDishOptionId);
}
