package fu.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fu.rms.entity.Material;
import fu.rms.entity.Status;
import fu.rms.newDto.Remain;

public interface MaterialRepository extends JpaRepository<Material, Long>{

	List<Material> findByStatus(Status status);
	
	Material findByMaterialCode(String materialCode);
	
	/*
	 * select remain theo id
	 */
	@Query(value="SELECT remain FROM materials WHERE material_id = :materialId", nativeQuery = true)
	Remain getRemainById(@Param("materialId") Long materialId);
	
}
