package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.Material;
import fu.rms.newDto.Remain;

public interface MaterialRepository extends JpaRepository<Material, Long>{
	/*
	 * tạo mới material
	 */
	@Modifying
	@Transactional
	@Query(name="insert.Material", nativeQuery = true)
	int insertMaterial(@Param("materialCode") String materialCode, @Param("materialName") String materialName, 
			@Param("unit") String unit, @Param("unitPrice") Double unitPrice, @Param("totalImport") Double totalImport,
			@Param("totalExport") Double totalExport, @Param("remain") Double remain, @Param("remainNotifycation") Double remainNotifycation, 
			@Param("groupId") Long groupId, @Param("statusId") Long statusId);
	
	/*
	 * select id của material mới nhất
	 */
	@Query(value="SELECT material_id FROM material ORDER BY material_id DESC LIMIT 1", nativeQuery = true)
	Long getLastestId();
	
	Material findByMaterialCode(String materialCode);
	
	/*
	 * select remain theo id
	 */
	@Query(value="SELECT remain FROM materials WHERE material_id = materialId", nativeQuery = true)
	Remain getRemainById(@Param("materialId") Long materialId);
	
}
