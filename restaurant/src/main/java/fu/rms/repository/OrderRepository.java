package fu.rms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	@Query(value="SELECT * FROM ORDERS o WHERE o.table_id = ?1", nativeQuery = true)
	List<Order> getListOrderByTable(Long table_id);
	
	@Query(value="SELECT * FROM ORDERS o WHERE o.order_code = ?1", nativeQuery = true)
	Order getOrderByCode(String orderCode);
	
	@Query(value="SELECT * FROM ORDERS o WHERE o.order_taker_id = ?1", nativeQuery = true)
	List<Order> getListOrderByOrderTaker(Long staffId);
	
	@Query(value="SELECT * FROM ORDERS o WHERE o.chef_id = ?1", nativeQuery = true)
	List<Order> getListOrderByChef(Long staffId);
	
	@Query(value="SELECT * FROM ORDERS o WHERE o.cashier_id = ?1", nativeQuery = true)
	List<Order> getListOrderByCashier(Long staffId);
	
	@Query(value="SELECT * FROM orders AS o WHERE o.table_id = ?1 "
			+ "ORDER BY o.order_date DESC LIMIT 1;", nativeQuery = true)
	Order getCurrentOrderByTable(Long tableId);
	
	@Query(value="SELECT * FROM orders AS o ORDER BY o.order_id DESC LIMIT 1", nativeQuery = true)
	Order getLastestOrder();
	
//	@Query(value="SELECT o.order_code FROM orders AS o ORDER BY o.order_id DESC LIMIT 1", nativeQuery = true)
//	String getOrderCodeLastestOrder();
	
	@Query(value="SELECT * FROM ORDERS o WHERE o.order_id = ?1", nativeQuery = true)
	Order getOrderById(Long orderId);
	
	
	/*
	 * tao moi order
	 */
	@Modifying
	@Transactional
	@Query(value="INSERT INTO Orders (order_taker_id, table_id, status_id,"
			+ " order_code, total_item, total_amount, order_date, create_by, time_to_complete) "
			+ "VALUES(:order_taker_id, :table_id, :status_id, "
			+ ":order_code, :total_item, :total_amount, :order_date, :create_by, 1)", nativeQuery = true)
	int insertOrder(@Param("order_taker_id") Long order_taker_id, @Param("table_id") Long table_id, 
			@Param("status_id") Long status_id, @Param("order_code") String order_code, 
			@Param("total_item") int total_item, @Param("total_amount") double total_amount, 
			@Param("order_date") Date order_date, @Param("create_by") String create_by);
	
	/*
	 * thay doi ve ban`
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.table_id = :table_id, modified_by = :modified_by, modified_date = :modified_date"
			+ "WHERE o.order_id = :order_id", nativeQuery = true)
	int updateOrderTable(@Param("table_id") Long tableId, @Param("modified_by") String modifiedBy, 
			@Param("modified_date") Date modifiedDate, @Param("order_id") Long orderId);
	
	/*
	 * thay doi ve trang thai
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.status = :status WHERE o.order_id = :order_id", nativeQuery = true)
	int updateOrderStatus(@Param("status") Long status, @Param("order_id") Long orderId);
	
	/*
	 * thay doi ve dau bep
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.chef_id = :chef_id, o.status = :status WHERE o.order_id = :order_id", nativeQuery = true)
	int updateOrderChef(@Param("chef_id") Long chefId,@Param("status") Long status, @Param("order_id") Long orderId);
	
	/*
	 * thay doi ve nguoi thu ngan
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.cashier_id = :cashier_id, o.status = :status WHERE o.order_id = :order_id", nativeQuery = true)
	int updateOrderCashier(@Param("cashier_id") Long cashierId,@Param("status") Long status, @Param("order_id") Long orderId);
	
	/*
	 * Thanh toan xong
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.payment_date = :payment_date, o.status = :status, o.time_to_complete = :time_to_complete "
			+ " WHERE o.order_id = :order_id", nativeQuery = true)
	int updatePayOrder(@Param("payment_date") Date paymentDate,@Param("status") Long status,
			@Param("time_to_complete") float timeToComplete, @Param("order_id") Long orderId);
	
	/*
	 * thay doi ve so luong, tong gia
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.total_item = :total_item, o.total_amount = :total_amount WHERE o.order_id = :order_id", nativeQuery = true)
	int updateOrderQuantity(@Param("total_item") int totalItem,@Param("total_amount") double totalAmount, @Param("order_id") Long orderId);
	
}
