package fu.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long>{
	
	@Query(name = "Option.findByStatusId")
	List<Option> findByStatusId(Long statusId);
	
	@Query(name = "Option.findByDishId")
	List<Option> findByDishId(Long dishId);
	
	@Transactional
	@Modifying
	@Query(name="Option.updateStatusId")
	int updateStatusId(Long optionId, Long statusId);
	
}