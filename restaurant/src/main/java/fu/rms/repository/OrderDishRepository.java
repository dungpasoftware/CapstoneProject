package fu.rms.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.OrderDish;
import fu.rms.newDto.SumQuantityAndPrice;

public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {

	/*
	 * select by order: cả hoàn thành hoặc chưa hoàn thành
	 */
	@Query
	(value="SELECT * FROM order_dish o WHERE o.order_id = ?1", nativeQuery = true)
	List<OrderDish> findOrderDishByOrder(Long orderId);
	
	/*
	 * select by id
	 */
	@Query
	(value="SELECT * FROM order_dish o WHERE o.order_dish_id = ?1", nativeQuery = true)
	OrderDish findOrderDishById(Long orderDishId);
	
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
	
	
	@Query
	(value="SELECT SUM(od.quantity) AS sumQuantity, SUM(od.sum_price) AS sumPrice FROM order_dish od WHERE od.order_id = :orderId AND od.status_id <> :statusCancel", nativeQuery = true)
	SumQuantityAndPrice getSumQtyAndPrice(@Param("orderId") Long orderId, @Param("statusCancel") Long statusCancel);
	
	@Query
	(value="SELECT COUNT(o.status_id) FROM order_dish o WHERE o.order_id = :orderId AND o.status_id <> :statusComplete AND od.status_id <> :statusCancel AND od.status_id <> :statusOkCancel", nativeQuery = true)
	Integer getCountCompleteOrder(@Param("orderId") Long orderId, @Param("statusComplete") Long statusComplete, @Param("statusCancel") Long statusCancel, @Param("statusOkCancel") Long statusOkCancel);
	
	@Query
	(value="SELECT od.order_dish_id FROM order_dish od WHERE o.order_id = :orderId", nativeQuery = true)
	List<Long> getOrderDishId(@Param("orderId") Long orderId);
	
	/*
	 * select lastest by order_id
	 */
	@Query
	(value="SELECT MAX(o.order_dish_id) FROM order_dish o WHERE o.order_id = ?1", nativeQuery = true)
	Long getLastestOrderDishId(Long orderId);
	
	
	/*
	 * select can return by order_id
	 */
	@Query
	(value="SELECT od.* FROM order_dish od INNER JOIN dishes d ON od.dish_id = d.dish_id "
			+ "WHERE d.type_return = true AND od.status_id = :statusId AND od.order_id = :orderId", nativeQuery = true)
	List<OrderDish> getCanReturnByOrderId(@Param("statusId") Long statusId, @Param("orderId") Long orderId);
	
	/*
	 * thêm món ăn
	 */
	@Modifying
	@Query
	(value="INSERT INTO order_dish (order_id, dish_id, comment, quantity, sell_price, sum_price, create_by, create_date, status_id)"
			+ "VALUES (:orderId, :dishId, :comment, :quantity, :sellPrice, :sumPrice, :createBy, :createDate, :statusId)", nativeQuery = true)
	int insertOrderDish(@Param("orderId") Long orderId, @Param("dishId") Long dishId, @Param("comment") String comment,
			@Param("quantity") Integer quantity, @Param("sellPrice") Double sellPrice, @Param("sumPrice") Double sumPrice,
			@Param("createBy") String createBy, @Param("createDate") Timestamp createDate, @Param("statusId") Long statusId);
	
	/*
	 * update khi nấu xong, trả món
	 * @param status
	 * @param orderDishId
	 * @return
	 */
	@Modifying
	@Query
	(value="UPDATE order_dish o SET o.status_id = :statusId WHERE o.order_dish_id = :order_dish_id", nativeQuery = true)
	int updateStatusOrderDish(@Param("statusId") Long statusId, @Param("order_dish_id") Long orderDishId);
	
	/*
	 * update hủy món cả order
	 * @param status
	 * @param orderId
	 */
	@Modifying
	@Query
	(value="UPDATE order_dish od SET od.status_id = :statusId, od.comment_cancel = :commentCancel, od.modified_date = :modifiedDate, od.modified_by = :modifiedBy WHERE od.order_id = :orderId", nativeQuery = true)
	int updateCancelOrderDishByOrder(@Param("statusId") Long statusId, @Param("commentCancel") String commentCancel, @Param("modifiedDate") Timestamp modifiedDate, 
			@Param("modifiedBy") String modifiedBy, @Param("orderId") Long orderId);
	
	
	/*
	 * update hủy từng món đã sử dụng nguyên liệu
	 */
	@Modifying
	@Transactional
	@Query
	(value="UPDATE order_dish od SET od.status_id = :statusId, od.comment_cancel = :commentCancel, od.quantity_cancel = :quantityCancel, od.quantity_ok =:quantityOk, od.sum_price= :sumPrice, "
			+ "od.modified_date = :modifiedDate, od.modified_by = :modifiedBy WHERE od.order_dish_id = :orderDishId", nativeQuery = true)
	int updateCancelOrderDish(@Param("statusId") Long statusId, @Param("commentCancel") String commentCancel, @Param("quantityCancel") Integer quantityCancel, @Param("quantityOk") Integer quantityOk, 
			@Param("sumPrice") Double sumPrice, @Param("modifiedDate") Timestamp modifiedDate, @Param("modifiedBy") String modifiedBy, @Param("orderDishId") Long orderDishId);
	
	
	/*
	 * xóa từng món khi chưa sử dụng nguyên liệu
	 * @param orderDishId
	 */
	@Modifying
	@Query
	(value="DELETE FROM order_dish WHERE order_dish_id = :orderDishId", nativeQuery = true)
	int deleteOrderDish(@Param("orderDishId") Long orderDishId);
	
	/*
	 * xóa món theo orderid khi chưa sử dụng nguyên liệu
	 * @param status
	 * @param orderId
	 */
	@Modifying
	@Query
	(value="DELETE FROM order_dish WHERE order_id = :orderId AND status_id = :statusId", nativeQuery = true)
	int deleteOrderDishByOrderId(@Param("orderId") Long orderId, @Param("statusId") Long statusId);
	
	/*
	 * update số lượng món (khi đã order xong)
	 * @param quantity
	 * @param sellPrice
	 * @param status
	 * @param orderDishId	
	 * @return
	 */
	@Modifying
	@Query
	(value="UPDATE order_dish od SET od.quantity = :quantity, od.sell_price = :sellPrice, od.sum_price = :sumPrice"
			+ " WHERE od.order_dish_id = :orderDishId", nativeQuery = true)
	int updateQuantityOrderDish(@Param("quantity") Integer quantity, @Param("sellPrice") Double sellPrice, 
			@Param("sumPrice") Double sumPrice, @Param("orderDishId") Long orderDishId);
	
	/*
	 * update comment
	 */
	@Modifying
	@Transactional
	@Query
	(value="UPDATE order_dish o SET comment = :comment WHERE o.order_dish_id = :orderDishId", nativeQuery = true)
	int updateCommentOrderDish(@Param("comment") String comment, @Param("orderDishId") Long orderDishId);
	
	
	/*
	 * update topping comment
	 */
	@Modifying
	@Transactional
	@Query
	(value="UPDATE order_dish od SET comment = :comment, od.sell_price = :sellPrice, od.sum_price = :sumPrice WHERE od.order_dish_id = :orderDishId", nativeQuery = true)
	int updateToppingComment(@Param("comment") String comment, @Param("sellPrice") Double sellPrice, @Param("sumPrice") Double sumPrice, @Param("orderDishId") Long orderDishId);

	
}
