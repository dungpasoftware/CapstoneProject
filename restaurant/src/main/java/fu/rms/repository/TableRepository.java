package fu.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.Tables;

public interface TableRepository extends JpaRepository<Tables, Long> {

	@Modifying
	@Transactional
	@Query( value = "update tables t set t.status_id = :status where t.table_id = :tableId", nativeQuery = true)
	int setStatus(@Param("tableId")Long tableId, @Param("status")Long status);
	
	@Query( value = "SELECT * FROM tables t WHERE t.location_id = ?1", nativeQuery = true)
	List<Tables> findTablesByLocation(Long locationId);
	
	@Modifying
	@Transactional
	@Query( value = "UPDATE tables t SET t.order_id = :order_id, t.status_id = :status, "
			+ "t.staff_id = :staff_id"
			+ " WHERE t.table_id = :table_id", nativeQuery = true)
	int updateTableNewOrder(@Param("order_id")Long orderId, @Param("staff_id")Long staffId, 
			@Param("table_id")Long tableId, @Param("status")Long status);
	
//	@Query( value = "SELECT * FROM tables", nativeQuery = true)
//	List<Tables> findTablesByLocation(Long locationId);
	
	@Modifying
	@Transactional
	@Query( value = "UPDATE tables t SET t.order_id = :order_id, t.status_id = :status, "
			+ "t.staff_id = :staff_id WHERE t.table_id = :table_id", nativeQuery = true)
	int updateTableChangeTable(@Param("order_id")Long orderId, @Param("staff_id")Long staffId, 
			@Param("table_id")Long tableId, @Param("status")Long status);
	
	@Modifying
	@Transactional
	@Query( value = "UPDATE tables t SET t.order_id = :order_id, t.status_id = :status, "
			+ "t.staff_id = :staff_id"
			+ " WHERE t.table_id = :table_id", nativeQuery = true)
	int update(@Param("order_id")Long orderId, @Param("staff_id")Long staffId, 
			@Param("table_id")Long tableId, @Param("status")Long status);
	
}
