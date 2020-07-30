package fu.rms.repository;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fu.rms.entity.Import;

public interface ImportRepository extends JpaRepository<Import, Long>{

//	/*
//	 * tạo mới import
//	 */
//	@Modifying
//	@Transactional
//	@Query(name="insert.Import", nativeQuery = true)
//	int insertImport(@Param("importCode") String importCode, @Param("importDate") Timestamp importDate, 
//			@Param("totalAmount") Double totalAmount, @Param("importBy") Long importBy, @Param("comment") String comment,
//			@Param("suppierId") Long suppierId, @Param("warehouseId") Long warehouseId);
	
	/*
	 * select id của import mới nhất
	 */
	@Query(value="SELECT import_id FROM import ORDER BY import_id DESC LIMIT 1", nativeQuery = true)
	Long getLastestId();
	
	Import findByImportCode(String importCode);
	
	
	@Query(value = "SELECT i.* " + 
			"FROM import AS i " + 
			"WHERE ( cast(i.created_date AS date) BETWEEN cast(:dateTo AS date)  AND cast(:dateFrom AS date)) " + 
			"AND (:supplierId is null or i.supplier_id = :supplierId)",
			countQuery = "SELECT i.* " + 
					"FROM import AS i " + 
					"WHERE (cast(i.created_date AS date) BETWEEN cast(:dateTo AS date)  AND cast(:dateFrom AS date)) " + 
					"AND (:supplierId is null or i.supplier_id = :supplierId)",
					nativeQuery = true)
	Page<Import> search(Long supplierId, Timestamp dateTo, Timestamp dateFrom, Pageable pageable);
}
