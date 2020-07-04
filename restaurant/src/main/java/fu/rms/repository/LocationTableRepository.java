package fu.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import fu.rms.interfacedto.LocationTableInterface;

import fu.rms.entity.LocationTable;

public interface LocationTableRepository extends JpaRepository<LocationTable,Long> {
	
	@Query(name = "get.LocationTable", nativeQuery = true)
	List<LocationTableInterface> getLo();

}
