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
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO Orders (order_taker_id, table_id, status_id,"
			+ " order_code, total_item, total_amount, order_date, create_by) "
			+ "VALUES(:order_taker_id, :table_id, :status_id, "
			+ ":order_code, :total_item, :total_amount, :order_date, :create_by)", nativeQuery = true)
	int insertOrder(@Param("order_taker_id") Long order_taker_id, @Param("table_id") Long table_id, 
			@Param("status_id") Long status_id, @Param("order_code") String order_code, 
			@Param("total_item") int total_item, @Param("total_amount") double total_amount, 
			@Param("order_date") Date order_date, @Param("create_by") String create_by);
	
}
