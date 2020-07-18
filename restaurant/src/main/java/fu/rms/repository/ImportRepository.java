package fu.rms.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.Import;

public interface ImportRepository extends JpaRepository<Import, Long>{

	/*
	 * tạo mới import
	 */
	@Modifying
	@Transactional
	@Query(name="insert.Import", nativeQuery = true)
	int insertImport(@Param("importCode") String importCode, @Param("importDate") Timestamp importDate, 
			@Param("totalAmount") Double totalAmount, @Param("importBy") Long importBy, @Param("comment") String comment,
			@Param("suppierId") Long suppierId, @Param("warehouseId") Long warehouseId);
	
	/*
	 * select id của import mới nhất
	 */
	@Query(value="SELECT import_id FROM import ORDER BY import_id DESC LIMIT 1", nativeQuery = true)
	Long getLastestId();
}
