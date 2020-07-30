package fu.rms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	
	@Query(value = "SELECT DISTINCT m.* " + 
			"FROM materials AS m " + 
			"LEFT JOIN group_material AS gm ON m.group_id = gm.group_id " + 
			"WHERE (:materialCode is null or m.material_code like CONCAT('%',:materialCode, '%')) " + 
			"AND (:groupId is null or gm.group_id = :groupId) "+
			"AND m.status_id = :statusId",
			countQuery = "SELECT COUNT(DISTINCT m.material_id) " + 
					"FROM materials AS m " + 
					"LEFT JOIN group_material AS gm ON m.group_id = gm.group_id " + 
					"WHERE (:materialCode is null or m.material_code like CONCAT('%',:materialCode, '%')) " + 
					"AND (:groupId is null or gm.group_id = :groupId) "+
					"AND m.status_id = :statusId",
			nativeQuery = true)
	Page<Material> search(String materialCode,Long groupId,Long statusId,Pageable pageable);
	
}
