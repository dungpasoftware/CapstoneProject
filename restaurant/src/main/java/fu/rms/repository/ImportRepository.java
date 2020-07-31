package fu.rms.repository;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fu.rms.entity.Import;

public interface ImportRepository extends JpaRepository<Import, Long>{


	@Query(value="SELECT import_id FROM import ORDER BY import_id DESC LIMIT 1", nativeQuery = true)
	Long getLastestId();
	
	Import findByImportCode(String importCode);
	
	
	@Query(value = "SELECT i.* " + 
			"FROM import AS i " + 
			"WHERE " + 
			"((cast(i.created_date AS date) BETWEEN cast(:dateFrom AS date) AND cast(:dateTo AS date)) " + 
			"OR ( cast(i.created_date AS date) >= :dateFrom AND :dateTo is null) " + 
			"OR ( cast(i.created_date AS date) <= :dateTo AND :dateFrom is null) " + 
			"OR (:dateFrom is null AND :dateTo is null)) " + 
			"AND (CASE WHEN :supplierId is null THEN 1=1 WHEN :supplierId = 0 THEN i.supplier_id is null ELSE i.supplier_id = :supplierId END)",
			countQuery = "SELECT COUNT(*) " + 
					"FROM import AS i " + 
					"WHERE " + 
					"((cast(i.created_date AS date) BETWEEN cast(:dateFrom AS date) AND cast(:dateTo AS date)) " + 
					"OR ( cast(i.created_date AS date) >= :dateFrom AND :dateTo is null) " + 
					"OR ( cast(i.created_date AS date) <= :dateTo AND :dateFrom is null) " + 
					"OR (:dateFrom is null AND :dateTo is null)) " + 
					"AND (CASE WHEN :supplierId is null THEN 1=1 WHEN :supplierId = 0 THEN i.supplier_id is null ELSE i.supplier_id = :supplierId END)",
					nativeQuery = true)
	Page<Import> search(Long supplierId, Timestamp dateFrom, Timestamp dateTo, Pageable pageable);
}
