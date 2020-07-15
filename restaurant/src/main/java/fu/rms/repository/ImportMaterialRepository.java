package fu.rms.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.ImportMaterial;

public interface ImportMaterialRepository extends JpaRepository<ImportMaterial, Long>{

	/*
	 * tạo mới import_material
	 */
	@Modifying
	@Transactional
	@Query(name="insert.ImportMaterial", nativeQuery = true)
	int insertImportMaterial(@Param("quantity") Double quantity, @Param("price") Double price, 
			@Param("sumPrice") Double sumPrice, @Param("expireDate") Timestamp expireDate, @Param("importId") Long importId,
			@Param("materialId") Long materialId);
}
