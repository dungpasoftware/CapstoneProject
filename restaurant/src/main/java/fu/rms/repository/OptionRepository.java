	package fu.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fu.rms.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long>{
	
	@Query(name = "Option.findByStatusId")
	List<Option> findByStatusId(Long statusId);
	
	@Query(name = "Option.findByDishIdAndStatusId")
	List<Option> findByDishIdAndStatusId(Long dishId, Long statusId);
	
	@Query(name = "Option.findByMaterialId")
	List<Option> findByMaterialId(Long materialId);
	
}
