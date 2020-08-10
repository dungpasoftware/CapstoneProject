package fu.rms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fu.rms.dto.ImportAndExportDto;
import fu.rms.dto.ImportMaterialDetailDto;
import fu.rms.entity.Material;
import fu.rms.newDto.Remain;

public interface MaterialRepository extends JpaRepository<Material, Long>{

	@Query(name = "Material.findByStatusId")
	List<Material> findByStatusId(Long statusId);
	
	Material findByMaterialCode(String materialCode);
	
	/*
	 * select remain theo id
	 */
	@Query(value="SELECT remain FROM materials WHERE material_id = :materialId", nativeQuery = true)
	Remain getRemainById(@Param("materialId") Long materialId);
	
	
	@Query(value = "SELECT m.* " + 
			"FROM materials AS m " + 
			"LEFT JOIN group_material AS gm ON m.group_id = gm.group_id " + 
			"WHERE " + 
			"CASE WHEN :materialCode IS NOT NULL THEN m.material_code like CONCAT('%',:materialCode, '%') ELSE 1=1 END " + 
			"AND " + 
			"CASE WHEN :groupId IS NOT NULL THEN gm.group_id = :groupId ELSE 1=1 END " + 
			"AND " + 
			"m.status_id = :statusId",
			countQuery = "SELECT COUNT(m.material_id) " + 
					"FROM materials AS m " + 
					"LEFT JOIN group_material AS gm ON m.group_id = gm.group_id " + 
					"WHERE " + 
					"CASE WHEN :materialCode IS NOT NULL THEN m.material_code like CONCAT('%',:materialCode, '%') ELSE 1=1 END " + 
					"AND " + 
					"CASE WHEN :groupId IS NOT NULL THEN gm.group_id = :groupId ELSE 1=1 END " + 
					"AND " + 
					"m.status_id = :statusId",
			nativeQuery = true)
	Page<Material> search(String materialCode,Long groupId,Long statusId,Pageable pageable);
	
	@Query(name = "Material.findImportAndExportByMaterialId")
	List<ImportAndExportDto> findImportAndExportById(Long materialId);
	
	@Query(name = "Material.findImportMaterialDetailByImportMaterialId")
	ImportMaterialDetailDto findImportMaterialDetailByImportMaterialId(Long importMaterialId);
	
}
